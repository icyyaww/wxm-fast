package com.wxm.msfast;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import com.wxm.msfast.base.common.handler.ServiceBlockExceptionHandler;
import org.checkerframework.checker.units.qual.A;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

@SpringBootApplication(exclude= {
        DataSourceAutoConfiguration.class,
        DruidDataSourceAutoConfigure.class})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ServiceBlockExceptionHandler.class})})
public class MsfastGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsfastGatewayApplication.class, args);
    }

}
