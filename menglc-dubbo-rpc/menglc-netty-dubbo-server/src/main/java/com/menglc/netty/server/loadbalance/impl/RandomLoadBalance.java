package com.menglc.netty.server.loadbalance.impl;

import com.menglc.netty.server.loadbalance.LoadBalance;

import java.util.List;
import java.util.Random;

/**
 * @author YLTDMenglc
 * @title:RandomLoadBalance
 * @description: 负载均衡：随机策略
 * @address
 * @date 2020/4/5  11:10
 */
public class RandomLoadBalance implements LoadBalance<String> {

    @Override
    public String selector(List<String> reponse) {
        String value = reponse.get(new Random().nextInt(reponse.size()));
        System.out.println(value);
        return value;
    }
}
