package com.wxm.msfast.role;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MsfastRoleApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsfastRoleApplication.class, args);
	}

}
