package com.menglc.service.api;

/**
 * @author YLTDMenglc
 * @title:MemberService
 * @description:    生产者接口
 * @address
 * @date 2020/4/5  11:39
 */
public interface MemberService {

    String getUserName(Long UserId);

    int updateUser(Long UserId);
}
