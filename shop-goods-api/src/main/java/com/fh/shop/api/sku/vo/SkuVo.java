package com.fh.shop.api.sku.vo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SkuVo {

    private Long id;

    private String skuName;

    private BigDecimal price;

    private String image;


}
