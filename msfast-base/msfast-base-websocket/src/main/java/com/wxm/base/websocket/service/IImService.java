package com.wxm.base.websocket.service;

import com.wxm.base.websocket.common.rest.response.ImUserInfoResponse;

public interface IImService {

    ImUserInfoResponse getImUser(Integer userId);
}
