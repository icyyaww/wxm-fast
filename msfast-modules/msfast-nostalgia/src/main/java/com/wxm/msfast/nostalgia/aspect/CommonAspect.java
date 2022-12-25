package com.wxm.msfast.nostalgia.aspect;

import com.wxm.msfast.base.common.service.ICommonAspect;
import org.springframework.stereotype.Service;

@Service
public class CommonAspect implements ICommonAspect {

    @Override
    public void afterReturning() {
    }
}
