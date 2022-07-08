package com.wxm.msfast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wxm.msfast.demo.feign")
@EnableRetry
public class MsfastDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastDemoApplication.class, args);
    }

}
