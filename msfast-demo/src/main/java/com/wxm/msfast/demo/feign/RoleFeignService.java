package com.wxm.msfast.demo.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("msfast-role")
public interface RoleFeignService {
}
