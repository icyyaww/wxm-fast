package com.wxm.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.nostalgia.entity.FrUserEntity;

public interface TestService extends IService<FrUserEntity> {

    void deleteFruser(Integer id);
}
