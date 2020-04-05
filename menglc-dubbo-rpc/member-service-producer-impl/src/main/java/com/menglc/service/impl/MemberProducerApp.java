package com.menglc.service.impl;

import com.menglc.netty.server.rpc.MenglcRpcServer;

/**
 * @author YLTDMenglc
 * @title:MemberProducerApp
 * @description:
 * @address
 * @date 2020/4/5  11:44
 */
public class MemberProducerApp {

    public static void main(String[] args) {
        new MenglcRpcServer("127.0.0.1",8080).run(new MemberServiceImpl());
    }
}
