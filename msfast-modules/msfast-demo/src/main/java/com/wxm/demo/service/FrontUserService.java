package com.wxm.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.demo.entity.FrontUserEntity;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-07-06 18:07:55
 */
public interface FrontUserService extends IService<FrontUserEntity> {

    void versionLock(Long id);
}


