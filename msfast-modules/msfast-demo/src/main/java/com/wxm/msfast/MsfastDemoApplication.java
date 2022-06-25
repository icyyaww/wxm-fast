package com.wxm.msfast;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.wxm.msfast.demo.feign")
@MapperScan({"com.wxm.msfast.demo.mapper", "com.wxm.msfast.base.server.mapper"})
//@MapperScans({"com.wxm.msfast.demo.mapper"})
public class MsfastDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastDemoApplication.class, args);
    }

}
