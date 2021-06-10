package com.fh.shop.api.member.vo;

import com.alibaba.fastjson.JSON;
import com.fh.util.Constants;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BaseController {


    public  MemberVo  buildMemberVo(HttpServletRequest request){
        try {
            String decode = URLDecoder.decode(request.getHeader(Constants.CURR_MEMBER), "utf-8");

            MemberVo memberVo = JSON.parseObject(decode, MemberVo.class);
            return memberVo;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }
}
