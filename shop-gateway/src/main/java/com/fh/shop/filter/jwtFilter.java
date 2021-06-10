package com.fh.shop.filter;

import com.alibaba.fastjson.JSON;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;

import com.fh.util.Constants;
import com.fh.util.Md5Util;
import com.fh.util.MemberVo;
import com.fh.util.RedisUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.sun.deploy.net.URLEncoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.List;

@Configuration
@Slf4j
public class jwtFilter extends ZuulFilter {




    @Value("${fh.shop.checkUrls}")
    private List<String> checkUrls;
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @SneakyThrows
    @Override
    public Object run() throws ZuulException {

        //获取当前访问的url
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String requestMethod = request.getMethod();
        if(requestMethod.equalsIgnoreCase("options")){
            currentContext.setSendZuulResponse(false);
            return null;
        }


        StringBuffer requestURL = request.getRequestURL();
        boolean isCheck =false;
        for (String checkUrl : checkUrls){

            if (requestURL.indexOf(checkUrl)>0){
                isCheck=true;
                break;

            }
        }

        if (!isCheck){
            //默认相当于放行
            return  null;
        }
        //需要进行验证的

        //当用户登陆后再次发送请求时判断是否带有头信息
        String token = request.getHeader("x-auth");
        if(StringUtils.isEmpty(token)){

            return buildResponse(ResponseEnum.TOKEN_IS_NULL);
        }
        String[] strings = token.split("\\.");
        //判断头信息长度是否为2如果不为2说明丢失
        if(strings.length!=2){

            return buildResponse(ResponseEnum.TOKEN_IS_LOST);
        }
        String memberJsonBase = strings[0];
        String signBase = strings[1];
        //把member信息转为json字符串
        String memberJson = new String(Base64.getDecoder().decode(memberJsonBase),"utf-8");
        //签名转为base64编码之前
        String sign=new String(Base64.getDecoder().decode(signBase),"utf-8");
        //判断前台传过来的签名和用户信息再次生成的签名是否一致
        if(!sign.equals(Md5Util.getSign(memberJson, Constants.LOGIN_SECRET))){

            return buildResponse(ResponseEnum.TOKEN_IS_WRONG);
        }
        MemberVo memberVo = JSON.parseObject(memberJson, MemberVo.class);
        Long id = memberVo.getId();
        //判断登陆是否超时
        if(!RedisUtil.exists(MemberUtil.getKey(id))){

            return  buildResponse(ResponseEnum.LOGIN_TIME_OUT);
        }
        //重新设置过期时间
        RedisUtil.expire(MemberUtil.getKey(id),Constants.TIME_OUT);
        request.setAttribute(Constants.MEMBER_INFO,memberVo);
        currentContext.addZuulRequestHeader("member", URLEncoder.encode(memberJson,"utf-8"));

        return null;
    }





    private    Object  buildResponse(ResponseEnum responseEnum){
        //不仅拦截了【不往后面走】而且还能给前台提示
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletResponse response = currentContext.getResponse();
        response.setContentType("application/json;charset=utf-8");
        currentContext.setSendZuulResponse(false);
        //提示json信息
        ServerResponse error = ServerResponse.error(responseEnum);
        String res = JSON.toJSONString(error);
        currentContext.setResponseBody(res);
        return  null;

    }
}
