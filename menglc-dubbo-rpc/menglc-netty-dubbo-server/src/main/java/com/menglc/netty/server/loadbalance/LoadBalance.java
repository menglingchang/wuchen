package com.menglc.netty.server.loadbalance;

import java.util.List;

/**
 * @author YLTDMenglc
 * @title:LoadBalance
 * @description:
 * @address
 * @date 2020/4/5  11:09
 */
public interface LoadBalance<T> {

    /**
     * 实现Dubbo负载均衡器 负载均衡、随机、一致性hash
     *
     * @param reponse
     * @return
     */
    String selector(List<T> reponse);
}
