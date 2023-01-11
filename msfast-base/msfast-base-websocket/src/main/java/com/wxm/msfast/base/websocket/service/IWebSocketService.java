package com.wxm.msfast.base.websocket.service;


import io.netty.channel.Channel;

public interface IWebSocketService {

    void read(Channel channel, String message);

    void close(Channel channel);

    void connect(Channel channel);
}
