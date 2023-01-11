package com.wxm.msfast.base.websocket.service.impl;

import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-11 14:32
 **/

@Service
public class IMessageServiceImpl implements IMessageService {
    @Override
    public void send(BaseMessageInfo messageInfo) {

        System.out.println("发送了一个消息");
    }
}
