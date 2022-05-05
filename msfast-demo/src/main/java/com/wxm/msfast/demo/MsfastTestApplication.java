package com.wxm.msfast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages="com.wxm.msfast.demo.feign")
public class MsfastTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastTestApplication.class, args);
    }

}
