package com.fh.shop.api.sku.biz;

import com.alibaba.fastjson.JSON;

import com.fh.shop.api.sku.mapper.ISkuMapper;
import com.fh.shop.api.sku.po.Sku;
import com.fh.shop.api.sku.vo.SkuVo;

import com.fh.common.ServerResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service("skuService")
@Transactional(rollbackFor = Exception.class)
public class ISkuServiceImpl implements ISkuService {

    @Autowired
    private ISkuMapper skuMapper;

    @Override
    public ServerResponse queryUpRecommedList() {



        List<Sku> skuList = skuMapper.selectList(null);
        List<SkuVo> skuVoList = skuList.stream().map(x -> {
            SkuVo skuVo = new SkuVo();
            skuVo.setId(x.getId());
            skuVo.setImage(x.getImage());
            skuVo.setPrice(x.getPrice());
            skuVo.setSkuName(x.getSkuName());
            return skuVo;
        }).collect(Collectors.toList());
//        RedisUtil.setEx("skuList",JSON.toJSONString(skuVoList),10);
        return ServerResponse.success(skuVoList);
    }

    @Override
    public ServerResponse findSkn(Long id) {

        Sku sku = skuMapper.selectById(id);

        return  ServerResponse.success(sku);
    }
}
