package com.wxm.msfast.base.websocket.thread;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSON;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.constant.Constants;
import com.wxm.msfast.base.common.rest.response.BaseUserInfo;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.websocket.common.constant.WebSocketConstants;
import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.common.rest.response.BaseMessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.ImUserInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageListResponse;
import com.wxm.msfast.base.websocket.service.IImService;
import com.wxm.msfast.base.websocket.service.MsFastMessageService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jacoco.agent.rt.internal_43f5073.core.internal.flow.IFrame;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-12 17:23
 **/
@Slf4j
public class SendRunnable implements Runnable {


    private ChannelUtil channelUtil;

    private BaseMessageInfo messageInfo;

    private RedisService redisService;

    MsFastMessageService msFastMessageService;

    public SendRunnable(ChannelUtil channelUtil, BaseMessageInfo messageInfo, RedisService redisService, MsFastMessageService msFastMessageService) {
        this.channelUtil = channelUtil;
        this.messageInfo = messageInfo;
        this.redisService = redisService;
        this.msFastMessageService = msFastMessageService;
    }

    @Override
    public void run() {
        log.info("开始发送消息");
        BaseMessageInfoResponse baseMessageInfoResponse = new BaseMessageInfoResponse();
        BeanUtils.copyProperties(messageInfo, baseMessageInfoResponse);
        baseMessageInfoResponse.setMsgNo(UUID.fastUUID().toString());

        //消息持久化
        MessageInfoResponse messageInfoResponse = new MessageInfoResponse();
        BeanUtils.copyProperties(messageInfo, messageInfoResponse);
        redisService.redisTemplate.opsForZSet().add(channelUtil.getMessageInfoKey(messageInfo.getSendUserId(), messageInfo.getAcceptUserId()), messageInfoResponse, System.currentTimeMillis());

        //修改未读数
        Integer unRead = redisService.getCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId());
        if (unRead == null) {
            redisService.setCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId(), 1);
        } else {
            redisService.setCacheObject(messageInfo.getAcceptUserId() + WebSocketConstants.MSG_UN_READ + messageInfo.getSendUserId(), unRead + 1);
        }

        //更新列表
        msFastMessageService.addMessageList(messageInfo, messageInfo.getSendUserId(), messageInfo.getAcceptUserId());
        msFastMessageService.addMessageList(messageInfo, messageInfo.getAcceptUserId(), messageInfo.getSendUserId());

        //发送消息
        channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));

        //todo 需要删除
        redisService.setCacheObject(baseMessageInfoResponse.getMsgNo(), Constants.MSG_ANSWER, ConfigConstants.RESEND_TIME(), TimeUnit.SECONDS);
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
