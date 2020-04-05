package com.menglc.netty.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author YLTDMenglc
 * @title:DubboClientHandler
 * @description:
 * @address
 * @date 2020/4/5  11:26
 */
public class DubboClientHandler extends ChannelInboundHandlerAdapter {

    public Object response;

    public Object getResponse() {
        return response;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg:" + msg);
        this.response = msg;
        // 客户端获取到服务端响应之后，主动关闭连接，默认长链接
        ctx.close();
    }
}
