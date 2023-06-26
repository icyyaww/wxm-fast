package com.wxm.demo.controller;

import java.util.Arrays;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wxm.base.common.web.domain.R;
import com.wxm.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wxm.demo.entity.UserEntity;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-06-28 11:32:34
 */
@RestController
@RequestMapping("demo/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("demo:user:info")
    public R info(@PathVariable("id") Long id) {
        UserEntity user = userService.getById(id);

        return R.ok(user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("demo:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("demo:user:update")
    public R update(@RequestBody UserEntity user) {
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("demo:user:delete")
    public R delete(@RequestBody Long[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/wrapper")
    public R wrapper() {
        userService.wrapper();
        return R.ok();
    }
}
