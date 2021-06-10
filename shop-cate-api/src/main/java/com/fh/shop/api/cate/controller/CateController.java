package com.fh.shop.api.cate.controller;


import com.fh.common.ServerResponse;
import com.fh.shop.api.cate.biz.ICateService;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;

@RestController
@RequestMapping("/api")
public class CateController {

    @Resource(name="cateService")
    private ICateService cateService;

    @RequestMapping(value = "/cates",method = RequestMethod.GET)

    public ServerResponse queryList(){
        return cateService.queryList();
    }
}
