package com.wxm.msfast.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = {"com.wxm.msfast"})
@EnableRetry
public class MsfastCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastCommunityApplication.class, args);
    }

}
