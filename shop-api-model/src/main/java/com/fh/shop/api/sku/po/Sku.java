package com.fh.shop.api.sku.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Sku implements Serializable {

    private Long id;

    private String skuName;

    private BigDecimal price;

    private Integer stock;

    private String specInfo;

    private Long spuId;

    private Long colorId;

    private String image;

    private Integer  status;

    private Integer recommend;

    private Integer  newProduct;


    private Integer  sales;



}
