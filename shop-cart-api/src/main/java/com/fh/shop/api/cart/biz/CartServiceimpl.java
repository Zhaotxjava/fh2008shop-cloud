package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSON;
import com.fh.common.ResponseEnum;

import com.fh.common.ServerResponse;
import com.fh.shop.api.cart.vo.CartItemVo;
import com.fh.shop.api.cart.vo.CartVo;

import com.fh.shop.api.common.KeyUtil;
import com.fh.shop.api.goods.IGoodsFeignService;
import com.fh.shop.api.sku.po.Sku;
import com.fh.util.BigdecimalUtil;
import com.fh.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service

@Slf4j
public class CartServiceimpl  implements CartService {

    @Autowired
    private IGoodsFeignService IGoodsFeignService;

    @Override
    public ServerResponse addItem(Long memberId, Long skuId, Long count) {

        // 商品是否存在
        ServerResponse<Sku> skuResponse = IGoodsFeignService.findSkn(skuId);

        log.info("xxxxxxxxxxxxxxxx",skuResponse);


        Sku  sku= (Sku) skuResponse.getData();


        if (sku == null) {
            return ServerResponse.error(ResponseEnum.MEMBER_Sku_IS_NULL);
        }

        // 商品是否上架
        if (sku.getStatus() == 0) {
            return ServerResponse.error(ResponseEnum.CART_SKU_IS_DOWN);
        }
        // 商品的库存量大于等于购买的数量
        Integer stock = sku.getStock();
        if (stock.intValue() < count) {
            return ServerResponse.error(ResponseEnum.CART_SKU_STOCK_IS_ERROR);
        }
        // 会员是否有购物车
        String key = KeyUtil.buildCartKey(memberId.toString());
        String cartJson = RedisUtil.get(key);
        // 如果没有购物车
        if (StringUtils.isEmpty(cartJson)) {
            // 创建一个购物车，直接把商品加入到购物车
            CartVo cartVo = new CartVo();
            CartItemVo cartItemVo = new CartItemVo();
            cartItemVo.setCount(count);
            String price = sku.getPrice().toString();
            cartItemVo.setPrice(price);
            cartItemVo.setSkuId(sku.getId());
            cartItemVo.setSkuImage(sku.getImage());
            cartItemVo.setSkuName(sku.getSkuName());
            BigDecimal subPrice = BigdecimalUtil.mul(price, count + "");
            cartItemVo.setSubPrice(subPrice.toString());
            cartVo.getCartItemVo().add(cartItemVo);
            cartVo.setTotalCount(count);
            cartVo.setTotalPrice(cartItemVo.getSubPrice());
            // 更新购物车【redis中得购物车】
            RedisUtil.set(key, JSON.toJSONString(cartVo));
        } else {
            // 如果有购车
            CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
            List<CartItemVo> cartItemVoList = cartVo.getCartItemVo();
            Optional<CartItemVo> item = cartItemVoList.stream().filter(x -> x.getSkuId().longValue() == skuId.longValue()).findFirst();
            if (item.isPresent()) {
                // 购物车有这款商品，找到这款商品，更新商品的数量，小计

                CartItemVo cartItemVo = item.get();
                long cound = cartItemVo.getCount() + count;
                if (cound<1){
                    return ServerResponse.success(ResponseEnum.MEMBER_CODE_IS_FALSQ);
                }
                cartItemVo.setCount(cound);

                BigDecimal subPrice = new BigDecimal(cartItemVo.getSubPrice());
                String subPriceStr = subPrice.add(BigdecimalUtil.mul(cartItemVo.getPrice(), count + "")).toString();
                cartItemVo.setSubPrice(subPriceStr);
                // 更新购物车
                List<CartItemVo> cartItemVos = cartVo.getCartItemVo();
                long totalCount = 0;
                BigDecimal totalPrice = new BigDecimal(0);
                for (CartItemVo itemVo : cartItemVos) {
                    totalCount += itemVo.getCount();
                    totalPrice = totalPrice.add(new BigDecimal(itemVo.getSubPrice()));
                }

                cartVo.setTotalCount(totalCount);
                cartVo.setTotalPrice(totalPrice.toString());
                // 更新购物车【redis中得购物车】
                RedisUtil.set(key, JSON.toJSONString(cartVo));
            } else {
                // 购物车中没有这款商品，直接将商品加入购物车
                CartItemVo cartItemVo = new CartItemVo();
                cartItemVo.setCount(count);
                String price = sku.getPrice().toString();
                cartItemVo.setPrice(price);
                cartItemVo.setSkuId(sku.getId());
                cartItemVo.setSkuImage(sku.getImage());
                cartItemVo.setSkuName(sku.getSkuName());
                BigDecimal subPrice = BigdecimalUtil.mul(price, count + "");
                cartItemVo.setSubPrice(subPrice.toString());
                cartVo.getCartItemVo().add(cartItemVo);
                List<CartItemVo> cartItemVos = cartVo.getCartItemVo();
                long totalCount = 0;
                BigDecimal totalPrice = new BigDecimal(0);
                for (CartItemVo itemVo : cartItemVos) {
                    totalCount += itemVo.getCount();
                    totalPrice = totalPrice.add(new BigDecimal(itemVo.getSubPrice()));
                }
                cartVo.setTotalCount(totalCount);
                cartVo.setTotalPrice(totalPrice.toString());
                // 更新购物车【redis中得购物车】
                RedisUtil.set(key, JSON.toJSONString(cartVo));
            }
        }
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findCart(Long memberId) {
        String cartJson = RedisUtil.get(KeyUtil.buildCartKey(memberId.toString()));
        CartVo cartVo = JSON.parseObject(cartJson, CartVo.class);
        return ServerResponse.success(cartVo);
    }
}
