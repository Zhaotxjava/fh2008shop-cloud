package com.fh.shop.api.common;

public class KeyUtil {

    public static String buildMemberKey(Long id){
        return "member:"+id;
    }

    public static String buildImageCodeKey(String id){
        return "image:code:"+id;
    }

    public static String buildActiveMemberKey(String id){
        return "action:member:"+id;
    }

    public static String buildCartKey(String id){
        return "cart:"+id;
    }
    public static String builTokenKey(String id){
        return "token:"+id;
    }



}
