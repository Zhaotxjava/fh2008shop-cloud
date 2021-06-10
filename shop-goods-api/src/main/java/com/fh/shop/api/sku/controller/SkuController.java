package com.fh.shop.api.sku.controller;

import com.fh.shop.api.sku.biz.ISkuService;

import com.fh.common.ServerResponse;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class SkuController {

    @Resource(name="skuService")
    private ISkuService skuService;

    @GetMapping("/skus")
    public ServerResponse findList(){
        return skuService.queryUpRecommedList();
    }


    @GetMapping("/skus/findSku")
    public ServerResponse findSkn(@RequestParam("id") Long id){

        return skuService.findSkn(id);
    }
}
