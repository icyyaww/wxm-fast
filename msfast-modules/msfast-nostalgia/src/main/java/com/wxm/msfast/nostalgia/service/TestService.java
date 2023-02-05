package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;

public interface TestService extends IService<FrUserEntity> {

    void deleteFruser(Integer id);
}
