package com.wxm.msfast.community.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.wxm.msfast.community.entity.SysConfigEntity;
import com.wxm.msfast.community.service.SysConfigService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
@RestController
@RequestMapping("community/sysconfig")
@Api(tags = "系统配置")
public class SysConfigController {

    @Autowired
    private SysConfigService sysConfigService;

    /**
     * 信息
     */
    @GetMapping("/value")
    @ApiOperation(value = "查询系统配置值")
    @ApiOperationSort(value = 1)
    @AuthIgnore
    public R<String> value(@RequestParam String code) {
        return R.ok(sysConfigService.getValueByCode(code));
    }
}
