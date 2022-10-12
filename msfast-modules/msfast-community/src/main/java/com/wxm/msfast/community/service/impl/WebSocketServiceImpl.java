package com.wxm.msfast.community.service.impl;

import com.wxm.msfast.base.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-12 17:28
 **/

@Service
public class WebSocketServiceImpl implements WebSocketService {
    @Override
    public void read(Channel channel, String message) {
        System.out.println("收到消息：" + message);
    }
}
