package com.wxm.msfast.base.websocket.service;

import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;

public interface IMessageService {

    void send(BaseMessageInfo messageInfo);

}
