package com.menglc.netty.server.registry.impl;

import com.menglc.netty.server.registry.Constant;
import com.menglc.netty.server.registry.ServiceRegistration;
import org.I0Itec.zkclient.ZkClient;

import java.net.URLEncoder;

/**
 * @author YLTDMenglc
 * @title:ServiceRegistrationImpl
 * @description: 将节点信息注册到zk上
 * @address
 * @date 2020/4/5  10:18
 */
public class ServiceRegistrationImpl implements ServiceRegistration {

    /**
     * zk连接地址
     */
    private final String zkServers = "127.0.0.1";

    /**
     * zkClient
     */
    private ZkClient zkClient;

    /**
     * zk节点根路径
     */
    private final String rootNamePath = "/menglc_rpc";

    /**
     * 服务地址路径
     */
    private final String providersPath = "/providers";

    public ServiceRegistrationImpl() {
        zkClient = new ZkClient(zkServers, Constant.ZK_SESSION_TIMEOUT);
    }

    @Override
    public void registry(String serviceName, String serviceAddress) {
        // 创建根路径
        if (!zkClient.exists(rootNamePath)) {
            zkClient.createPersistent(rootNamePath);
        }
        // 创建接口路径
        String serviceNodePath = rootNamePath + "/" + serviceName;
        if (!zkClient.exists(serviceNodePath)) {
            zkClient.createPersistent(serviceNodePath);
        }
        // 创建服务地址目录
        String providerNodePath = serviceNodePath + providersPath;
        if (!zkClient.exists(providerNodePath)){
            zkClient.createPersistent(providerNodePath);
        }
        // 创建服务地址
        String serviceAddresNodePath = providerNodePath + "/" + URLEncoder.encode(serviceAddress);
        if (zkClient.exists(serviceAddresNodePath)){
            zkClient.delete(serviceAddresNodePath);
        }
        zkClient.createEphemeral(serviceAddresNodePath);
    }
}
