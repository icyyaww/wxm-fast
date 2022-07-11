package com.wxm.msfast.base.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.role.entity.RoleEntity;

import java.util.Map;

/**
 * 
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-11 10:59:21
 */
public interface RoleService extends IService<RoleEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

