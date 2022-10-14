package com.wxm.msfast.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication(scanBasePackages = {"com.wxm.msfast"})
@ServletComponentScan
@EnableRetry
public class MsfastCommunityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastCommunityApplication.class, args);
    }

}
