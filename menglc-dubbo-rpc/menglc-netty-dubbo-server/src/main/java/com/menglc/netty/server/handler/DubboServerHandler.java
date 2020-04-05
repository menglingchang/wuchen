package com.menglc.netty.server.handler;

import com.menglc.netty.server.req.RpcRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author YLTDMenglc
 * @title:DubboServerHandler
 * @description: 监听器，监听接收到的请求，并根据反射技术执行生产者方法，然后返回给客户端
 * @address
 * @date 2020/4/5  10:49
 */
public class DubboServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 存放注册的bean对象
     */
    private Map<String, Object> serviceBean = new HashMap<>();

    public DubboServerHandler(Map<String, Object> serviceBean) {
        this.serviceBean = serviceBean;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取请求参数对象
        RpcRequest rpcRequest = (RpcRequest) msg;
        String className = rpcRequest.getClassName();
        Object objectImpl = serviceBean.get(className);
        if (objectImpl == null) {
            throw new NullPointerException();
        }
        // 使用反射技术获取方法
        Method method = objectImpl.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParameterTypes());
        // 使用反射技术执行方法
        Object result = method.invoke(objectImpl, rpcRequest.getParamsValue());
        // 响应客户端
        ctx.writeAndFlush(result);
    }

}
