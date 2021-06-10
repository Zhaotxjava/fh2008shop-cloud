package com.fh.shop.api.sku.biz;


import com.fh.common.ServerResponse;

public interface ISkuService {

   ServerResponse queryUpRecommedList();

    ServerResponse findSkn(Long id);
}
