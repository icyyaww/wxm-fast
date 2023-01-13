package com.wxm.msfast.base.websocket.service.impl;

import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.service.MsFastMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    @Override
    public Set<MessageInfoResponse> getMessageInfoRange(String key, Long start, Long end) {

        Set<MessageInfoResponse> set = redisService.redisTemplate.opsForZSet().reverseRange(key, start, end);

        return set;
    }
}
