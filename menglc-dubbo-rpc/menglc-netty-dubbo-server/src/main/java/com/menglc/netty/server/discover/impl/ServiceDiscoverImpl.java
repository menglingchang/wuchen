package com.menglc.netty.server.discover.impl;

import com.menglc.netty.server.discover.ServiceDiscover;
import com.menglc.netty.server.registry.Constant;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @author YLTDMenglc
 * @title:ServiceDiscoverImpl
 * @description: 服务发现
 * @address
 * @date 2020/4/5  11:06
 */
public class ServiceDiscoverImpl implements ServiceDiscover {

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

    public ServiceDiscoverImpl() {
        // 连接zk
        zkClient = new ZkClient(zkServers, Constant.ZK_SESSION_TIMEOUT);
    }

    @Override
    public List<String> getDiscover(String serviceName) {
        String serviceNameNodePath = rootNamePath + "/" + serviceName + providersPath;
        List<String> children = zkClient.getChildren(serviceNameNodePath);
        return children;
    }
}
