package com.wxm.msfast.test.controller;

import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.utils.R;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @program: msfast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-04-29 17:48
 **/
@RestController
@RequestMapping("test")
public class TestController {

    /**
     * 列表
     */
    @GetMapping("/role/info/{id}")
    public R roleInfo(@PathVariable Long id) {
        return R.ok().put("page", "哈哈");
    }
}
