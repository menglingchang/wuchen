package com.menglc.netty.server.discover;

import java.util.List;

/**
 * @author YLTDMenglc
 * @title:ServiceDiscover
 * @description: 服务发现
 * @address
 * @date 2020/4/5  11:05
 */
public interface ServiceDiscover {

    /**
     * 根据服务名称查找对应服务列表
     * @param serviceName
     * @return
     */
    List<String> getDiscover(String serviceName);
}
