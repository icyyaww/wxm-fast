package com.wxm.msfast.base.websocket.service.impl;

import com.wxm.msfast.base.common.constant.Constants;
import com.wxm.msfast.base.common.rest.response.BaseUserInfo;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.base.websocket.common.constant.WebSocketConstants;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageListResponse;
import com.wxm.msfast.base.websocket.service.MsFastMessageService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-13 16:29
 **/
@Service
public class MsFastMessageServiceImpl implements MsFastMessageService {

    @Autowired
    RedisService redisService;

    @Autowired
    ChannelUtil channelUtil;


    @Override
    public PageResult<MessageInfoResponse> getMessageInfoRange(Integer userId, Integer pageIndex, Integer pageSize) {

        Set<MessageInfoResponse> reverseRange = redisService.redisTemplate.opsForZSet().reverseRange(channelUtil.getMessageInfoKey(TokenUtils.getOwnerId(), userId), (pageIndex - 1) * pageSize, (pageIndex - 1) * pageSize + pageSize - 1);
        Long total = redisService.redisTemplate.opsForZSet().size(channelUtil.getMessageInfoKey(TokenUtils.getOwnerId(), userId));
        PageResult<MessageInfoResponse> result = new PageResult<>(total, pageIndex, pageSize, reverseRange);
        updateUnRead(userId);
        return result;
    }

    @Async
    void updateUnRead(Integer userId) {

        redisService.setCacheObject(TokenUtils.getOwnerId() + WebSocketConstants.MSG_UN_READ + userId, 0);

        Set<MessageListResponse> listResponses = redisService.redisTemplate.opsForZSet().range(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), 0, -1);
        Optional<MessageListResponse> optional = listResponses.stream().filter(p -> p.getSendUserId() != null && p.getSendUserId().equals(userId)).findFirst();

        if (optional.isPresent()) {
            MessageListResponse messageListResponse = optional.get();
            Double score = redisService.redisTemplate.opsForZSet().score(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), messageListResponse);
            messageListResponse.setUnreadCount(0);
            redisService.redisTemplate.opsForZSet().add(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), messageListResponse, score);
        }

    }

    @Override
    public PageResult<MessageListResponse> getMessageListRange(Integer pageIndex, Integer pageSize) {
        Set<MessageListResponse> listResponses = redisService.redisTemplate.opsForZSet().reverseRange(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), 0, -1);
        Long total = 0l;
        if (listResponses != null) {
            total = Long.valueOf(listResponses.size());
        }
        Set<MessageListResponse> reverseRange = listResponses.stream()
                .skip((pageIndex - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toSet());
        PageResult<MessageListResponse> result = new PageResult<>(total, pageIndex, pageSize, reverseRange);
        return result;
    }

    @Override
    public void deleteList(Integer sendUserId) {

        Set<MessageListResponse> listResponses = redisService.redisTemplate.opsForZSet().range(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), 0, -1);
        Optional<MessageListResponse> optional = listResponses.stream().filter(p -> p.getSendUserId() != null && p.getSendUserId().equals(sendUserId)).findFirst();
        if (optional.isPresent()) {
            MessageListResponse messageListResponse = optional.get();
            redisService.redisTemplate.opsForZSet().remove(WebSocketConstants.MSG_LIST + TokenUtils.getOwnerId(), messageListResponse);
        }
    }


}
