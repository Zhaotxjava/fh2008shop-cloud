package com.fh.shop.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.fh.shop.api.member.mapper")
public class ShopMemberApp {
    public static void main(String[] args)  {
        // Class.forName(" org.slf4j.impl.Log4jLoggerFactory");


        SpringApplication.run(ShopMemberApp.class,args);
    }
}
