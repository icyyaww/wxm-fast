package com.wxm.api.auth.fallback;

import com.wxm.api.auth.feign.AuthFeignService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-17 11:17
 **/

@Component
public class AuthFeignServiceFallBack implements FallbackFactory<AuthFeignService> {
    @Override
    public AuthFeignService create(Throwable cause) {
        return null;
    }
}
