package com.wxm.demo.controller;

import com.wxm.base.common.annotation.AuthIgnore;
import com.wxm.base.common.constant.ConfigConstants;
import com.wxm.base.common.exception.JrsfException;
import com.wxm.base.common.utils.JwtUtils;
import com.wxm.base.common.web.domain.R;
import com.wxm.demo.common.rest.request.UserAddRequest;
import com.wxm.demo.exception.DemoExceptionEnum;
import com.wxm.demo.feign.RoleFeignService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: msfast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-04-29 17:48
 **/
@RefreshScope
@RestController
@RequestMapping("test")
@Api(tags = "演示程序")
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
        result.put("redisOpen", ConfigConstants.AUTH_REDIS_ENABLE());
        return R.ok(result);
    }

    /**
     * @Description: 数据校验测试
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/8 下午4:37
     */
    @PostMapping("/add")
    @Transactional
    @AuthIgnore
    public R addUser(@Valid @RequestBody UserAddRequest request) {
        return R.ok();
    }

    /**
     * @Description: 异常处理测试
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/12 下午2:21
     */
    @PostMapping("/exception")
    public R exception(@Valid @RequestBody UserAddRequest request) {
        if (request.getAge().compareTo(18) < 0) {
            throw new JrsfException(DemoExceptionEnum.AGE_MIN);
        }
        return R.ok();
    }

    /**
     * @Description: 创建jwt
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/25 上午10:55
     */
    @PostMapping("/jwt")
    public R jwt() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("username", "张三");
        return R.ok(JwtUtils.createToken(paramMap));
    }

    /**
     * @Description: 获取静态变量文件
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/6/25 上午10:55
     */
    @GetMapping("/properties")
    @ApiOperation("获取静态变量文件")
    public R getProperties() {
        Set<String> set = new LinkedHashSet<>();

        return R.ok(ConfigConstants.AUTHENTICATION());
    }

}
