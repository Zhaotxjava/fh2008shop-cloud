package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.api.sku.mapper")
public class ShopGoodsApp {
    public static void main(String[] args)  {
        // Class.forName(" org.slf4j.impl.Log4jLoggerFactory");


        SpringApplication.run(ShopGoodsApp.class,args);
    }
}
