package com.menglc.netty.server.proxy;

import com.menglc.netty.server.discover.ServiceDiscover;
import com.menglc.netty.server.discover.impl.ServiceDiscoverImpl;
import com.menglc.netty.server.handler.DubboClientHandler;
import com.menglc.netty.server.loadbalance.LoadBalance;
import com.menglc.netty.server.loadbalance.impl.LoopLoadBalance;
import com.menglc.netty.server.marshalling.MarshallingCodeCFactory;
import com.menglc.netty.server.req.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import javax.xml.ws.Service;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author YLTDMenglc
 * @title:RpcClientProxy
 * @description: 客户端远程代理：消费端使用动态代理模式调用服务端接口
 * @address
 * @date 2020/4/5  11:14
 */
public class RpcClientProxy {

    private ServiceDiscover serviceDiscover;

    private DubboClientHandler dubboClientHandler;

    private NioEventLoopGroup group;

    private Bootstrap bootstrap;

    public RpcClientProxy() {
        serviceDiscover = new ServiceDiscoverImpl();
    }

    public <T> T create(Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass},
                new InvocationHandler() {

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 使用代理拼接地址
                String serviceName = interfaceClass.getName();
                List<String> discover = serviceDiscover.getDiscover(serviceName);
                // 采用默认轮训的负载均衡器
                LoadBalance loadBalance = new LoopLoadBalance();
                String select = URLDecoder.decode(loadBalance.selector(discover));
                // 获取到地址 menglc://192.168.110:8080
                String[] split = select.split(":");
                String host = split[1].replace("//", "");
                String port = split[2].replace("/","");
                // 封装具体调用的参数
                RpcRequest rpcRequest = new RpcRequest(serviceName, method.getName(), method.getParameterTypes(), args);
                // 启动netty客户端发送消息
                return sendMsg(host, Integer.parseInt(port), rpcRequest);
            }
        });
    }

    private Object sendMsg(String host, int port, RpcRequest rpcRequest) {
        dubboClientHandler = new DubboClientHandler();

        // 创建NioEventLoopGroup
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group).channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host,port))
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(dubboClientHandler);
                    }
                });

        // 发起同步连接
        try {
            ChannelFuture sync = bootstrap.connect().sync();
            // 向客户端发送对象
            System.out.println("生产者发送消息:" + rpcRequest.toString());
            sync.channel().writeAndFlush(rpcRequest);
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
        return dubboClientHandler.getResponse();
    }
}
