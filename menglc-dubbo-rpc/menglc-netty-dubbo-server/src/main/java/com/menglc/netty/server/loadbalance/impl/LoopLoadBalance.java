package com.menglc.netty.server.loadbalance.impl;

import com.menglc.netty.server.loadbalance.LoadBalance;

import java.util.List;

/**
 * @author YLTDMenglc
 * @title:LoopLoadBalance
 * @description: 负载均衡：轮训策略
 * @address
 * @date 2020/4/5  11:11
 */
public class LoopLoadBalance implements LoadBalance<String> {

    private int index = 0;

    @Override
    public synchronized String selector(List<String> reponse) {
        int size = reponse.size();
        if (index >= size) {
            index = 0;
        }
        String value = reponse.get(index++);
        System.out.println("value:" + value);
        return value;
    }
}
