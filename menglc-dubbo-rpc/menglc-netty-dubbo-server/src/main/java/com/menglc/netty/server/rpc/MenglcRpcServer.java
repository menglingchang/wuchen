package com.menglc.netty.server.rpc;

import com.menglc.netty.annotation.RpcAnnotation;
import com.menglc.netty.server.handler.DubboServerHandler;
import com.menglc.netty.server.marshalling.MarshallingCodeCFactory;
import com.menglc.netty.server.registry.ServiceRegistration;
import com.menglc.netty.server.registry.impl.ServiceRegistrationImpl;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YLTDMenglc
 * @title:MenglcRpcServer
 * @description:
 * @address
 * @date 2020/4/5  10:31
 */
public class MenglcRpcServer {

    /**
     * 存放注册的bean对象
     */
    private Map<String, Object> serviceBean = new HashMap<>();

    private ServiceRegistration serviceRegistration;

    /**
     * 服务注册端口号
     */
    private int port;

    /**
     * 服务地址
     */
    private String host;

    /**
     * 用于接受客户端连接的请求 （并没有处理请求）
     */
    private NioEventLoopGroup bossGroup;

    /**
     * 用于处理客户端连接的读写操作
     */
    private NioEventLoopGroup workerGroup;

    private ServerBootstrap serverBootstrap;

    public MenglcRpcServer() {

    }

    public MenglcRpcServer(String host,int port) {
        this.host = host;
        this.port = port;
        serviceRegistration = new ServiceRegistrationImpl();
    }

    public void run(Object object) {
        // 先把服务注册到zk
        bind(object);
        // 启动netty
        nettyStart();
    }

    private void bind(Object object) {
        // 获取需要发布的接口的注解class类
        RpcAnnotation declaredAnnotation = object.getClass().getDeclaredAnnotation(RpcAnnotation.class);
        if (declaredAnnotation == null) {
            throw new NullPointerException();
        }
        Class value = declaredAnnotation.value();
        String serviceName = value.getName();
        String serviceAddress = "menglc://" + host + ":" + port + "/";
        serviceRegistration.registry(serviceName, serviceAddress);
        System.out.println("serviceName " + serviceName + ",serviceAddress " + serviceAddress);
        serviceBean.put(serviceName, object);
    }

    private void nettyStart() {
        // 接收客户端请求，并不做处理
        bossGroup = new NioEventLoopGroup();
        // 处理客户端请求
        workerGroup = new NioEventLoopGroup();
        // 创建serverBootStrap,初始化netty服务器，并且开始监听端口的socket请求
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                        socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                        socketChannel.pipeline().addLast(new DubboServerHandler(serviceBean));
                    }
                });

        // 绑定端口号码
        try {
            // 绑定端口号，同步等待成功
            ChannelFuture future = serverBootstrap.bind(port).sync();
            System.out.println("生产者服务启动成功:" + port);
            // 等待服务器监听端口
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }


}
