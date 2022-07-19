package com.wxm.msfast.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(scanBasePackages = "com.wxm.msfast")
@EnableFeignClients(basePackages = "com.wxm.msfast.demo.feign")
@EnableRetry
@EnableSwagger2
public class MsfastDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastDemoApplication.class, args);
    }

}
