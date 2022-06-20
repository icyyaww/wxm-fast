package com.wxm.msfast.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.role.entity.UserEntity;

import java.util.Map;

/**
 * 系统用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-04-29 16:53:44
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

