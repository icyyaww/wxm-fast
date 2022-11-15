package com.wxm.msfast.role.controller;

import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.role.entity.UserEntity;
import com.wxm.msfast.role.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

//import org.apache.shiro.authz.annotation.RequiresPermissions;



/**
 * 系统用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-04-29 16:53:44
 */
@RestController
@RequestMapping("role/user")
public class UserController {
    @Autowired
    private UserService userService;


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("role:user:info")
    public R info(@PathVariable("userId") Long userId){
		UserEntity user = userService.getById(userId);

        return R.ok(user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("role:user:save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("role:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("role:user:delete")
    public R delete(@RequestBody Long[] userIds){
		userService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
