package com.wxm.base.websocket.service;

import com.wxm.base.common.utils.PageResult;
import com.wxm.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.base.websocket.common.rest.response.MessageListResponse;


public interface MsFastMessageService {

    PageResult<MessageInfoResponse> getMessageInfoRange(Integer sendUserId, Integer pageIndex, Integer pageSize);

    PageResult<MessageListResponse> getMessageListRange(Integer pageIndex, Integer pageSize);

    void deleteList(Integer sendUserId);

    Integer unRead();

    void addMessageList(BaseMessageInfo messageInfo, Integer userId, Integer sendUserId);
}
