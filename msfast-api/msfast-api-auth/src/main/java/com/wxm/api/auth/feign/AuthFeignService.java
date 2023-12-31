package com.wxm.api.auth.feign;

import com.wxm.api.auth.fallback.AuthFeignServiceFallBack;
import com.wxm.api.auth.rest.response.LoginUserResponse;
import com.wxm.api.auth.rest.request.LoginUserRequest;
import com.wxm.base.common.constant.ServiceNameConstants;
import com.wxm.base.common.web.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@FeignClient(name = ServiceNameConstants.DEMO_SERVICE, path = "token", fallbackFactory = AuthFeignServiceFallBack.class)
public interface AuthFeignService {

    /**
     * @Description: 登陆 方法需要实现
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/17 下午2:12
     */
    @PostMapping("/login")
    R<LoginUserResponse> login(@Valid @RequestBody LoginUserRequest request);
}
