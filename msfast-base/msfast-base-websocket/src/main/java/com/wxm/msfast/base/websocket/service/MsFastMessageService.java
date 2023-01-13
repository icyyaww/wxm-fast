package com.wxm.msfast.base.websocket.service;

import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;

import java.util.Set;

public interface MsFastMessageService {

    Set<MessageInfoResponse> getMessageInfoRange(String key, Long start, Long end);
}
