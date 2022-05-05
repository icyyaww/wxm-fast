package com.wxm.msfast.demo.controller;

import com.wxm.msfast.base.common.utils.R;
import com.wxm.msfast.demo.feign.RoleFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: msfast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-04-29 17:48
 **/
@RestController
@RequestMapping("demo")
public class TestController {

    @Autowired
    RoleFeignService roleFeignService;

    /**
     * 角色详情
     */
    @GetMapping("/role/{id}")
    public R roleInfo(@PathVariable(name = "id") Long id) {
        return roleFeignService.info(id);
    }
}
