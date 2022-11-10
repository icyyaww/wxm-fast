package com.wxm.msfast.base.websocket.utils;

import com.alibaba.fastjson2.JSON;
import com.wxm.msfast.base.websocket.common.constant.Constants;
import com.wxm.msfast.base.websocket.common.rest.request.UserSendMessage;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-13 17:29
 **/

@Component
public class ChannelUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    public void sendText(Channel channel, String text) {
        if (channel != null) {
            channel.writeAndFlush(new TextWebSocketFrame(text));
        }
    }

    public void sendText(Integer userId, String text) {
        UserSendMessage userSendMessage = new UserSendMessage();
        userSendMessage.setId(userId);
        userSendMessage.setContent(text);
        redisTemplate.convertAndSend(Constants.REDIS_CHANNEL_MESSAGE, JSON.toJSONString(userSendMessage));
    }

}
