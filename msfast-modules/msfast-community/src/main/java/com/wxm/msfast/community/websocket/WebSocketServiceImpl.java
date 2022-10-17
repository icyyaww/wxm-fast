package com.wxm.msfast.community.websocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;

import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.websocket.netty.ChannelMap;
import com.wxm.msfast.base.websocket.service.IWebSocketService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import com.wxm.msfast.community.common.constant.Constants;
import com.wxm.msfast.community.common.enums.MessageTypeEnum;
import com.wxm.msfast.community.common.type.MatchingType;
import com.wxm.msfast.community.common.type.MessageInfo;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-12 17:28
 **/

@Service
public class WebSocketServiceImpl implements IWebSocketService {

    @Resource
    private RedisService redisService;

  /*  @Autowired
    RedissonClient redissonClient;*/

    @Override
    public void read(Channel channel, String text) {
        MessageInfo message = JSON.parseObject(text, MessageInfo.class);
        if (MessageTypeEnum.MATCHING.equals(message.getMessageType())) {
            if (StringUtils.isNotBlank(message.getInfo())) {
                String info = message.getInfo().replaceAll("'", "\"");
                MatchingType matchingType = JSON.parseObject(info, MatchingType.class);
                Channel connect = ChannelMap.get(matchingType.getUserId());
                if (connect == null) {
                    ChannelUtil.sendText(channel, "未绑定连接");
                } else {
                    //开始匹配
                    matching(matchingType);
                   /* RLock lock = redissonClient.getLock(Constants.MATCHING_LOCK);
                    try {
                        //开始匹配
                        matching(message.getInfo());
                    } finally {
                        lock.unlock();
                    }*/

                }
            }
        }
    }

    private void matching(MatchingType matchingType) {
        redisService.setCacheObject(Constants.MATCHING + matchingType.getUserId(), matchingType.getUserId(), 10l, TimeUnit.MINUTES);
        Collection<String> keys = redisService.keys(Constants.MATCHING + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            keys.forEach(model -> {
                if (StringUtils.isNotBlank(model) && !model.equals(Constants.MATCHING + matchingType.getUserId())) {
                    Channel channel = ChannelMap.get(Integer.valueOf(matchingType.getUserId()));
                    Channel channelMatch = ChannelMap.get(Integer.valueOf(model.substring(Constants.MATCHING.length())));
                    if (channel != null && channelMatch != null) {
                        ChannelUtil.sendText(channel, model.substring(Constants.MATCHING.length()));
                        ChannelUtil.sendText(channelMatch, matchingType.getUserId().toString());
                        return;
                    }
                }
            });
        }
    }
}
