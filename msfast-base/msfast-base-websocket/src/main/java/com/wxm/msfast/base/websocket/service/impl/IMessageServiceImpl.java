package com.wxm.msfast.base.websocket.service.impl;

import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.ThreadUtil;
import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.service.IMessageService;
import com.wxm.msfast.base.websocket.thread.SendRunnable;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-11 14:32
 **/

@Service
public class IMessageServiceImpl implements IMessageService {

    @Autowired
    private ChannelUtil channelUtil;

    @Autowired
    private RedisService redisService;

    @Override
    public void send(BaseMessageInfo messageInfo) {
        ThreadUtil.getInstance().cachedThreadPool.execute(new SendRunnable(channelUtil, messageInfo, redisService));
    }
}
