package com.fh.shop.api.member.biz;


import com.fh.common.ServerResponse;

public interface IMemberService {


    public ServerResponse login(String memberName, String password);

}

