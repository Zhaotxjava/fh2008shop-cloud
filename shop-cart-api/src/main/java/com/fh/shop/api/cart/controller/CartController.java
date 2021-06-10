package com.fh.shop.api.cart.controller;


import com.fh.common.ServerResponse;

import com.fh.shop.api.cart.biz.CartService;

import com.fh.shop.api.member.vo.BaseController;
import com.fh.shop.api.member.vo.MemberVo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/carts")
public class CartController  extends BaseController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CartService cartService;

    @PostMapping("/addCartItem")
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "skuId", value = "商品id", dataType = "java.lang.Long", required = true),
            @ApiImplicitParam(name = "count", value = "商品数量", dataType = "java.lang.Long", required = true),
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse addCartItem(Long skuId, Long count) {
       MemberVo memberVo = buildMemberVo(request);

        Long memberId = memberVo.getId();

        return cartService.addItem(memberId, skuId, count);
    }


    @GetMapping("/findCart")
    @ApiOperation("查找购物车商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth", value = "头信息", dataType = "java.lang.String", required = true, paramType = "header")
    })
    public ServerResponse findCart() {
        MemberVo memberVo = buildMemberVo(request);
        Long memberId = memberVo.getId();
        return cartService.findCart(memberId);
    }




}
