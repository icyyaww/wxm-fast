package com.wxm.msfast.community.websocket;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson2.JSON;

import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.websocket.netty.ChannelMap;
import com.wxm.msfast.base.websocket.service.IWebSocketService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import com.wxm.msfast.community.common.constant.Constants;
import com.wxm.msfast.community.common.enums.MessageTypeEnum;
import com.wxm.msfast.community.common.rest.response.matching.MatchSuccessResponse;
import com.wxm.msfast.community.common.type.MatchingType;
import com.wxm.msfast.community.common.type.MessageInfo;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
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

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    FrUserService frUserService;

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

                    RLock lock = redissonClient.getLock(Constants.MATCHING_LOCK);
                    try {
                        lock.lock();
                        //开始匹配
                        matching(matchingType);
                    } finally {
                        lock.unlock();
                    }

                }
            }
        }
    }

    @Override
    public void close(Channel channel) {
        Map.Entry<Integer, Channel> map = ChannelMap.getManager().entrySet().stream().filter(p -> p.getValue().equals(channel)).findFirst().orElse(null);
        if (map != null) {
            redisService.deleteObject(Constants.MATCHING + map.getKey());
            redisService.deleteObject(Constants.MATCHING_SUCCESS + map.getKey());
        }
    }

    @Override
    public void connect(Channel channel) {

    }

    private void matching(MatchingType matchingType) {


        Long successTime = redisService.getExpire(Constants.MATCHING_SUCCESS + matchingType.getUserId(), TimeUnit.SECONDS);
        if (successTime > 0) {
            return;
        }

        redisService.setCacheObject(Constants.MATCHING + matchingType.getUserId(), matchingType.getUserId(), 5l, TimeUnit.MINUTES);
        Collection<String> keys = redisService.keys(Constants.MATCHING + "*");
        if (CollectionUtil.isNotEmpty(keys)) {
            keys.forEach(model -> {
                if (StringUtils.isNotBlank(model) && !model.equals(Constants.MATCHING + matchingType.getUserId())) {
                    Channel channel = ChannelMap.get(Integer.valueOf(matchingType.getUserId()));
                    Integer otherUserId = Integer.valueOf(model.substring(Constants.MATCHING.length()));
                    Channel channelMatch = ChannelMap.get(otherUserId);
                    if (channel != null && channelMatch != null) {
                        //成功
                        //todo 使用多线程优化 减少等待时间
                        FrUserEntity otherUser = frUserService.getById(otherUserId);
                        FrUserEntity selfUser = frUserService.getById(matchingType.getUserId());
                        if (otherUser != null && selfUser != null) {
                            MatchSuccessResponse matchSuccessResponse = new MatchSuccessResponse();
                            BeanUtils.copyProperties(otherUser, matchSuccessResponse);
                            matchSuccessResponse.setHeadPortraitSelf(selfUser.getHeadPortrait());
                            matchSuccessResponse.setMessage("恭喜你");
                            ChannelUtil.sendText(channel, JSON.toJSONString(matchSuccessResponse));

                            MatchSuccessResponse matchSuccessOther = new MatchSuccessResponse();
                            BeanUtils.copyProperties(selfUser, matchSuccessOther);
                            matchSuccessOther.setHeadPortraitSelf(otherUser.getHeadPortrait());
                            matchSuccessOther.setMessage("恭喜你");
                            ChannelUtil.sendText(channelMatch, JSON.toJSONString(matchSuccessOther));

                        }

                        redisService.setCacheObject(Constants.MATCHING_SUCCESS + matchingType.getUserId(), matchingType.getUserId(), 5l, TimeUnit.MINUTES);
                        redisService.setCacheObject(Constants.MATCHING_SUCCESS + model.substring(Constants.MATCHING.length()), model.substring(Constants.MATCHING.length()), 5l, TimeUnit.MINUTES);

                        redisService.deleteObject(Constants.MATCHING + matchingType.getUserId());
                        redisService.deleteObject(Constants.MATCHING + model.substring(Constants.MATCHING.length()));
                        return;
                    }
                }
            });
        }
    }
}
