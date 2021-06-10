package com.fh.shop.api.member.biz;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.common.ResponseEnum;
import com.fh.common.ServerResponse;


import com.fh.shop.api.member.mapper.IMemberMapper;
import com.fh.shop.api.member.po.Member;
import com.fh.shop.api.member.vo.MemberUtil;
import com.fh.shop.api.member.vo.MemberVo;


import com.fh.util.Constants;
import com.fh.util.Md5Util;

import com.fh.util.RedisUtil;

import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;


@Service("memberService")
@Transactional(rollbackFor = Exception.class)
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private IMemberMapper memberMapper;


    @Override
    @Transactional(readOnly = true)
    public ServerResponse login(String memberName, String password) {
        //非空判断
         if(StringUtils.isEmpty(memberName) || StringUtils.isEmpty(password)){
            return ServerResponse.error(ResponseEnum.MEMBER_INFO_IS_NULL);
        }
        //判断用户是否存在
        QueryWrapper<Member> memberQueryWrapper = new QueryWrapper<>();
        memberQueryWrapper.eq("memberName",memberName);
        Member member = memberMapper.selectOne(memberQueryWrapper);
        if(member==null){
            return ServerResponse.error(ResponseEnum.MEMBER_IS_NOT_EXISTS);
        }
        //判断账号是否正确
        if(!member.getPassword().equals(Md5Util.md5(password))){
           return ServerResponse.error(ResponseEnum.PASSWORD_IS_NOT_SAME) ;
        }
//        if (!member.getStatus().equals(1)){
//            String uuid = UUID.randomUUID().toString();
//            String mail = member.getMail();
//            RedisUtil.setEx(KeyUtil.buildActiveMemberKey(uuid),member.getId()+"",6*60);
//            Map<String, String> result=new HashMap<>();
//            result.put("uuid",uuid);
//            result.put("mail",mail);
//            return ServerResponse.error(ResponseEnum.MEMBER_CODE_IS_jh,result);
//        }

        //生成签名
        //把要展示的数据存放到VO里边
        MemberVo memberVo = new MemberVo();
        Long id = member.getId();
        memberVo.setId(id);
        memberVo.setMemberName(member.getMemberName());
        memberVo.setNickName(member.getNickName());


        //转成一个字符串用来加密
        String voJson = JSON.toJSONString(memberVo);
        //把用户信息和密钥结合起来MD5散列
        String sign = Md5Util.getSign(voJson , Constants.LOGIN_SECRET);
        //把用户信息用base64编码
        String memberInfoBase = Base64.getEncoder().encodeToString(voJson.getBytes());
        //把签名用base64编码
        String signBase = Base64.getEncoder().encodeToString(sign.getBytes());
        //把用户信息和签名用.分割返回到前台

        RedisUtil.setEx(MemberUtil.getKey(id),"",Constants.TIME_OUT);



        return ServerResponse.success(memberInfoBase+"."+signBase);
    }







}
