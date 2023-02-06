package com.wxm.msfast.base.websocket.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.github.pagehelper.Page;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.service.MsFastMessageService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        List<Integer> userIdList = reverseRange.stream().filter(p -> p.getSendUserId() != null).map(MessageInfoResponse::getSendUserId).collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(userIdList)) {

        }
        PageResult<MessageInfoResponse> result = new PageResult<>(total, pageIndex, pageSize, reverseRange);
        return result;
    }
}
