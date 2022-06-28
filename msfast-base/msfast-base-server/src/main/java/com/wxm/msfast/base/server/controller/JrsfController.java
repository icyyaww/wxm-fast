package com.wxm.msfast.base.server.controller;

import cn.hutool.core.lang.reflect.MethodHandleUtil;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.base.server.rest.request.BaseRequest;
import com.wxm.msfast.base.server.service.JrsfService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-25 16:25
 **/
@RestController
@RequestMapping("/api")
public class JrsfController {

    @PostMapping("/{name}")
    public <T> R addUser(@PathVariable String name, @Valid @RequestBody T request) {
        JrsfService jrsfService = SpringUtils.getBean(name + "Service");
        jrsfService.add(request);
        return R.ok();
    }
}
