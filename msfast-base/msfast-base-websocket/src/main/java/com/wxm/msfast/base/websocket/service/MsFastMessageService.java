package com.wxm.msfast.base.websocket.service;

import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;

import java.util.Set;

public interface MsFastMessageService {

    PageResult<MessageInfoResponse> getMessageInfoRange(Integer userId, Integer pageIndex, Integer pageSize);
}
