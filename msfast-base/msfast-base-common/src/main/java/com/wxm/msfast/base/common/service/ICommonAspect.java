package com.wxm.msfast.base.common.service;

import org.springframework.scheduling.annotation.Async;

public interface ICommonAspect {

    @Async
    void afterReturning();
}
