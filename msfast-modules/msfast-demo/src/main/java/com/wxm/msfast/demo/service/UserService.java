package com.wxm.msfast.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.demo.entity.UserEntity;

import java.util.Map;

/**
 *
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-06-28 11:32:34
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void wrapper();
}

