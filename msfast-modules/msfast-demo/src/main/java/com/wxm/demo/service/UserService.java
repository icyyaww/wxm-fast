package com.wxm.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.demo.entity.UserEntity;

/**
 *
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-06-28 11:32:34
 */
public interface UserService extends IService<UserEntity> {


    void wrapper();
}

