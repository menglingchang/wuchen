package com.menglc.service.impl;

/**
 * @author YLTDMenglc
 * @title:ConsumerApp
 * @description:
 * @address
 * @date 2020/4/5  11:50
 */
public class ConsumerApp {

    public static void main(String[] args) {
        OrderServiceImpl orderService = new OrderServiceImpl();
        String ordersUserName = orderService.getOrdersUserName(10L);
        System.out.println("result:" + ordersUserName);
    }
}
