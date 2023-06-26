package com.wxm.base.websocket.service;

import com.wxm.base.websocket.common.rest.request.BaseMessageInfo;

public interface IMessageService {

    void send(BaseMessageInfo messageInfo);

}
