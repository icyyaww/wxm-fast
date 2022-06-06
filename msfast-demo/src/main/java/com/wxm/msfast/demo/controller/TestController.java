package com.wxm.msfast.demo.controller;

import com.wxm.msfast.base.common.utils.R;
import com.wxm.msfast.demo.feign.RoleFeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: msfast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-04-29 17:48
 **/
@RefreshScope
@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    RoleFeignService roleFeignService;

    @Value("${ms.user.name}")
    String userName;

    @Value("${ms.user.age}")
    int age;

    /**
     * @Description: 测试openFeign 远程调用角色权限服务
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/5/31 下午3:47
     */
    @GetMapping("/role/{id}")
    public R roleInfo(@PathVariable(name = "id") Long id) {
        return roleFeignService.info(id);
    }

    /**
     * @Description: 测试读取nacos配置中心 远程配置
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/5/31 下午3:47
     */
    @GetMapping("/config")
    public R nacosConfig() {
        Map<String, Object> result = new HashMap<>();
        result.put("userName", userName);
        result.put("age", age);
        return R.ok(result);
    }
}
