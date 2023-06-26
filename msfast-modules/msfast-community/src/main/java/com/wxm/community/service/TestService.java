package com.wxm.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.community.entity.FrUserEntity;

public interface TestService extends IService<FrUserEntity> {

    void deleteFruser(Integer id);
}
