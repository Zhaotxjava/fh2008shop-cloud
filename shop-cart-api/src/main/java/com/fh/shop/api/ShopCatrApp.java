package com.fh.shop.api;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShopCatrApp {
    public static void main(String[] args)  {
        // Class.forName(" org.slf4j.impl.Log4jLoggerFactory");

        SpringApplication.run(ShopCatrApp.class,args);
    }
}
