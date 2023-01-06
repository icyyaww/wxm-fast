package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.nostalgia.common.constant.Constants;
import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import com.wxm.msfast.nostalgia.common.enums.AuthTypeEnum;
import com.wxm.msfast.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import com.wxm.msfast.nostalgia.common.rest.response.front.fruser.AdditionalResponse;
import com.wxm.msfast.nostalgia.dao.FrUserAuthDao;
import com.wxm.msfast.nostalgia.entity.FrUserAuthEntity;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserAuthService;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("frUserAuthService")
public class FrUserAuthServiceImpl extends ServiceImpl<FrUserAuthDao, FrUserAuthEntity> implements FrUserAuthService {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    FrUserService frUserService;

    @Override
    @Transactional
    public void addAuth(DoubleAuthRequest request) {

        RLock lock = redissonClient.getLock(Constants.ADD_AUTH + TokenUtils.getOwnerId());
        try {
            lock.lock();
            FrUserEntity frUserEntity = frUserService.getById(TokenUtils.getOwnerId());
            if (frUserEntity != null) {
                FrUserAuthEntity frUserAuthEntity = new FrUserAuthEntity();
                BeanUtils.copyProperties(request, frUserAuthEntity);
                frUserAuthEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
                frUserAuthEntity.setUserId(frUserEntity.getId());
                this.save(frUserAuthEntity);
                if (AuthTypeEnum.EducationAuth.equals(request.getAuthType())) {
                    if (frUserEntity.getAdditional() != null) {
                        frUserEntity.getAdditional().setEducationAuth(AuthStatusEnum.EXAMINE);
                    } else {
                        AdditionalResponse additional = new AdditionalResponse();
                        additional.setEducationAuth(AuthStatusEnum.EXAMINE);
                        frUserEntity.setAdditional(additional);
                    }
                } else if (AuthTypeEnum.IdentityAuth.equals(request.getAuthType())) {
                    if (frUserEntity.getAdditional() != null) {
                        frUserEntity.getAdditional().setIdentityAuth(AuthStatusEnum.EXAMINE);
                    } else {
                        AdditionalResponse additional = new AdditionalResponse();
                        additional.setIdentityAuth(AuthStatusEnum.EXAMINE);
                        frUserEntity.setAdditional(additional);
                    }
                }
                frUserService.updateById(frUserEntity);
            }

        } finally {
            lock.unlock();
        }
    }

}
