package com.menglc.netty.server.registry;

/**
 * @author YLTDMenglc
 * @title:ServiceRegistration
 * @description:
 * @address
 * @date 2020/4/5  10:17
 */
public interface ServiceRegistration {

    /**
     * 注册节点信息到zk上
     * @param serviceName
     * @param serviceAddress
     */
    void registry(String serviceName,String serviceAddress);
}
