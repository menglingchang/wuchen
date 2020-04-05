package com.menglc.service.impl;

import com.menglc.netty.server.proxy.RpcClientProxy;
import com.menglc.service.api.MemberService;
import com.menglc.service.api.OrderService;

/**
 * @author YLTDMenglc
 * @title:OrderServiceImpl
 * @description:
 * @address
 * @date 2020/4/5  11:52
 */
public class OrderServiceImpl implements OrderService {
    public String getOrdersUserName(Long UserId) {
        // 代理模式生成类
        MemberService memberService = new RpcClientProxy().create(MemberService.class);
        String userName = memberService.getUserName(10L);
        return userName;
    }

}
