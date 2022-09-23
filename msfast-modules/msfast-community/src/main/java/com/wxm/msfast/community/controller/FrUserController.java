package com.wxm.msfast.community.controller;

import java.util.Arrays;
import java.util.Map;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.domain.R;


/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
@RestController
@RequestMapping("community/fruser")
public class FrUserController {
    @Autowired
    private FrUserService frUserService;


}
