package com.wxm.msfast.base.websocket.service;


import io.netty.channel.Channel;

public interface WebSocketService {
    void read(Channel channel, String message);
}
