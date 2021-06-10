package com.fh.shop.api.sku.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateSkuVo implements Serializable {

    private Long skuId;

    private int  stock;

}
