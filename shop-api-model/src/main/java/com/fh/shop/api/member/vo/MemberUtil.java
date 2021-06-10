package com.fh.shop.api.member.vo;

public class MemberUtil {

    public static String getKey(Long id){
        return "member:"+id;
    }

    public static String getUUID(String uuid){

        return "image:code:"+uuid;
    }
}
