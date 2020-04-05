package com.menglc.netty.server.registry.impl;

/**
 * @author YLTDMenglc
 * @title:Test
 * @description:
 * @address
 * @date 2020/4/5  11:57
 */
public class Test {

    public static void main(String[] args) {
        ServiceRegistrationImpl serviceRegistration = new ServiceRegistrationImpl();
        String rpcAddres = "mayikt://192.168.8.11:8082/";
        serviceRegistration.registry("com.menglc.api.service.UserService",rpcAddres);
    }
}
