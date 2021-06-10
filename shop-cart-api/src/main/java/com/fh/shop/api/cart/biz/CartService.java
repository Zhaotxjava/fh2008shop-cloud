package com.fh.shop.api.cart.biz;

import com.fh.common.ServerResponse;

public interface CartService {


    ServerResponse addItem(Long memberId, Long skuId, Long count);

    ServerResponse findCart(Long memberId);
}
