package com.wxm.demo.feign;

import com.wxm.base.common.constant.ServiceNameConstants;
import com.wxm.base.common.web.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = ServiceNameConstants.ROLE_SERVICE,path = "role/user")
public interface RoleFeignService {

    @RequestMapping("/info/{userId}")
    R info(@PathVariable("userId") Long userId);
}
