package com.fh.shop.api.member.controller;

import com.fh.common.ServerResponse;

import com.fh.shop.api.member.biz.IMemberService;

import com.fh.shop.api.member.vo.BaseController;
import com.fh.shop.api.member.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@RestController
@Api(tags = "会员信息管理")
@RequestMapping("/api")
public class MemberController  extends BaseController {

    @Resource(name="memberService")
    private IMemberService memberService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping("/member/login")
    @ApiOperation(value = "登陆", notes = "通过会员名称和密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name="memberName",value = "会员名",dataType = "java.lang.String",required = true),
            @ApiImplicitParam(name="password",value = "密码",dataType = "java.lang.String",required = true)
    })
    public ServerResponse login(String memberName, String password){

        return memberService.login(memberName,password);
    }


    @GetMapping("/member/findMember")

    @ApiOperation(value = "查找会员", notes = "请求中获取")
    @ApiImplicitParam(name="mail",value = "邮箱",paramType="header",required = true)
    public ServerResponse findMember(){
        MemberVo memberVo = buildMemberVo(request);
        return ServerResponse.success(memberVo);
    }





}
