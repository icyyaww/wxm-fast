package com.wxm.msfast.base.role.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wxm.msfast.base.role.rest.request.RoleAddRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wxm.msfast.base.role.entity.RoleEntity;
import com.wxm.msfast.base.role.service.RoleService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-11 10:59:21
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("role:role:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = roleService.queryPage(params);

        return R.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("role:role:info")
    public R info(@PathVariable("id") Integer id) {
        RoleEntity role = roleService.getById(id);

        return R.ok(role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("role:role:save")
    public R save(@RequestBody RoleEntity role) {
        roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("role:role:update")
    public R update(@RequestBody RoleEntity role) {
        roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("role:role:delete")
    public R delete(@RequestBody Integer[] ids) {
        roleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody RoleAddRequest request) {
        roleService.add(request);
        return R.ok();
    }
}
