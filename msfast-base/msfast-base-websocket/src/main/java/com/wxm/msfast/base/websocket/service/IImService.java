package com.wxm.msfast.base.websocket.service;

import com.wxm.msfast.base.websocket.common.rest.response.ImUserInfoResponse;

public interface IImService {

    ImUserInfoResponse getImUser(Integer userId);
}
