package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.common.rest.request.auth.DoubleAuthRequest;
import com.wxm.msfast.nostalgia.common.rest.response.front.auth.AuthResponse;
import com.wxm.msfast.nostalgia.entity.FrUserAuthEntity;


/**
 * 用户认证
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2023-01-03 16:57:02
 */
public interface FrUserAuthService extends IService<FrUserAuthEntity> {

    void addAuth(DoubleAuthRequest request);

    AuthResponse info();
}

