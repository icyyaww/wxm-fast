package com.wxm.msfast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages="com.wxm.msfast.demo.feign")
public class MsfastDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastDemoApplication.class, args);
    }

}
