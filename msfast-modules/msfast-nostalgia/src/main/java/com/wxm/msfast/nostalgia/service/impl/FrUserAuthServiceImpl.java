package com.wxm.msfast.nostalgia.service.impl;

import com.wxm.msfast.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.FrUserAuthDao;
import com.wxm.msfast.nostalgia.entity.FrUserAuthEntity;
import com.wxm.msfast.nostalgia.service.FrUserAuthService;


@Service("frUserAuthService")
public class FrUserAuthServiceImpl extends ServiceImpl<FrUserAuthDao, FrUserAuthEntity> implements FrUserAuthService {

    @Override
    public void addAurh(DoubleAuthRequest request) {

    }
}
