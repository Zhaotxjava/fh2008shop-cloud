package com.fh.shop.filter;

public class MemberUtil {

    public static String getKey(Long id){
        return "member:"+id;
    }

    public static String getUUID(String uuid){

        return "image:code:"+uuid;
    }
}
