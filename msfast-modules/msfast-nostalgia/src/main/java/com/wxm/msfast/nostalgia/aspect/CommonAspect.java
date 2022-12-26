package com.wxm.msfast.nostalgia.aspect;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.service.ICommonAspect;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CommonAspect implements ICommonAspect {

    @Autowired
    FrUserService frUserService;

    @Override
    @Transactional
    public void afterReturning() {

        Integer userId = TokenUtils.getOwnerId();
        if (userId != null) {
            this.frUserService.updateLatelyTime(userId);
        }

    }
}
