package com.menglc.service.impl;

import com.menglc.netty.annotation.RpcAnnotation;
import com.menglc.service.api.MemberService;

/**
 * @author YLTDMenglc
 * @title:MemberServiceImpl
 * @description:
 * @address
 * @date 2020/4/5  11:41
 */
@RpcAnnotation(MemberService.class)
public class MemberServiceImpl implements MemberService {
    public String getUserName(Long UserId) {
        return "风清扬";
    }

    public int updateUser(Long UserId) {
        return 0;
    }
}
