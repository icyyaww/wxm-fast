package com.wxm.base.auth.controller;

import com.wxm.base.auth.service.MsfConfigService;
import com.wxm.base.auth.common.enums.ConfigAccessEnum;
import com.wxm.base.auth.entity.MsfConfigEntity;
import com.wxm.base.common.annotation.AuthIgnore;
import com.wxm.base.common.enums.BaseExceptionEnum;
import com.wxm.base.common.exception.JrsfException;
import com.wxm.base.common.web.domain.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import org.apache.shiro.authz.annotation.RequiresPermissions;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-10-10 15:45:08
 */
@RestController
@RequestMapping("msfast/sysconfig")
@Api(tags = "系统配置")
public class MsfConfigController {

    @Autowired
    private MsfConfigService msfConfigService;

    /**
     * 信息
     */
    @GetMapping("/value")
    @ApiOperation(value = "查询系统配置值")
    @ApiOperationSort(value = 1)
    @AuthIgnore
    public R<String> value(@RequestParam String code) {
        MsfConfigEntity msfConfigEntity = msfConfigService.getConfigByCode(code);
        if (msfConfigEntity != null) {

            if (ConfigAccessEnum.INNER.equals(msfConfigEntity.getAccess())) {
                throw new JrsfException(BaseExceptionEnum.NO_PERMISSION_EXCEPTION);
            }
            return R.ok(msfConfigEntity.getValue());
        }
        return R.ok();
    }
}
