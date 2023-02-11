package com.wxm.msfast.base.websocket.service;

import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageListResponse;


public interface MsFastMessageService {

    PageResult<MessageInfoResponse> getMessageInfoRange(Integer sendUserId, Integer pageIndex, Integer pageSize);

    PageResult<MessageListResponse> getMessageListRange(Integer pageIndex, Integer pageSize);

    void deleteList(Integer sendUserId);

    Integer unRead();
}
