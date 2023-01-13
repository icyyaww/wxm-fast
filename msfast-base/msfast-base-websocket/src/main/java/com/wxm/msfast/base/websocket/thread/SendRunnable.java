package com.wxm.msfast.base.websocket.thread;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.websocket.common.constant.Constants;
import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.common.rest.response.BaseMessageInfoResponse;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-12 17:23
 **/

public class SendRunnable implements Runnable {


    private ChannelUtil channelUtil;

    private BaseMessageInfo messageInfo;

    private RedisService redisService;

    public SendRunnable(ChannelUtil channelUtil, BaseMessageInfo messageInfo, RedisService redisService) {
        this.channelUtil = channelUtil;
        this.messageInfo = messageInfo;
        this.redisService = redisService;
    }

    @Override
    public void run() {

        BaseMessageInfoResponse baseMessageInfoResponse = new BaseMessageInfoResponse();
        BeanUtils.copyProperties(messageInfo, baseMessageInfoResponse);
        baseMessageInfoResponse.setMsgNo(UUID.fastUUID().toString());
        //发送消息
        redisService.redisTemplate.opsForZSet().add(channelUtil.getMessageInfoKey(messageInfo.getSendUserId(),messageInfo.getAcceptUserId()),messageInfo,System.currentTimeMillis());
        channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));
        redisService.setCacheObject(baseMessageInfoResponse.getMsgNo(), Constants.MSG_ANSWER, Long.parseLong(String.valueOf(ConfigConstants.HEART_BEAT_TIME())), TimeUnit.SECONDS);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String value = redisService.getCacheObject(baseMessageInfoResponse.getMsgNo());
            if (StringUtils.isNotBlank(value)) {
                channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));
            } else {
                break;
            }
        }


    }
}
