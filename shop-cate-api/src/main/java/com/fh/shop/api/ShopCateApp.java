package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.api.cate.mapper")
public class ShopCateApp {
    public static void main(String[] args)  {
        // Class.forName(" org.slf4j.impl.Log4jLoggerFactory");


        SpringApplication.run(ShopCateApp.class,args);
    }
}
