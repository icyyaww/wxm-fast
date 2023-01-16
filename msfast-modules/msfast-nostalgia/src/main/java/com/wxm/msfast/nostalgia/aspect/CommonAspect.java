package com.wxm.msfast.nostalgia.aspect;

import com.wxm.msfast.base.common.service.ICommonAspect;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
