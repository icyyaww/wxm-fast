package com.wxm.msfast.demo.feign;

import com.wxm.msfast.base.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "msfast-role",path = "role/user")
public interface RoleFeignService {

    @RequestMapping("/info/{userId}")
    R info(@PathVariable("userId") Long userId);
}
