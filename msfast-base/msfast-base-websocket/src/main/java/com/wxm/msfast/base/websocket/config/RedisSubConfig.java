package com.wxm.msfast.base.websocket.config;

import com.wxm.msfast.base.websocket.common.constant.Constants;
import com.wxm.msfast.base.websocket.service.RedisMessageListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisSubConfig {

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory factory, RedisMessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        //订阅频道redis.news 和 redis.life  这个container 可以添加多个 messageListener
        container.addMessageListener(listener, new ChannelTopic(Constants.REDIS_CHANNEL_MESSAGE));
        //container.addMessageListener(listener, new ChannelTopic("redis.news"));
        return container;
    }
}