package com.wxm.msfast.base.websocket.utils;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-13 17:29
 **/

public class ChannelUtil {

    public static void sendText(Channel channel, String text) {
        channel.writeAndFlush(new TextWebSocketFrame(text));
    }
}
