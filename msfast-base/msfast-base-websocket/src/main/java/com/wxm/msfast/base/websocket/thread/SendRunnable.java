package com.wxm.msfast.base.websocket.thread;

import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson2.JSON;
import com.wxm.msfast.base.common.constant.Constants;
import com.wxm.msfast.base.common.rest.response.BaseUserInfo;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.websocket.common.constant.WebSocketConstants;
import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.common.rest.response.BaseMessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.common.rest.response.MessageListResponse;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import org.jacoco.agent.rt.internal_43f5073.core.internal.flow.IFrame;
import org.springframework.beans.BeanUtils;

import java.util.Date;

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
        updateList(messageInfo, messageInfo.getSendUserId(), messageInfo.getAcceptUserId());
        updateList(messageInfo, messageInfo.getAcceptUserId(), messageInfo.getSendUserId());

        //发送消息
        channelUtil.sendText(messageInfo.getAcceptUserId(), JSON.toJSONString(baseMessageInfoResponse));

        //todo 删除
        /*redisService.setCacheObject(baseMessageInfoResponse.getMsgNo(), Constants.MSG_ANSWER, Long.parseLong(String.valueOf(ConfigConstants.HEART_BEAT_TIME())), TimeUnit.SECONDS);
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
        }*/


    }

    private void updateList(BaseMessageInfo messageInfo, Integer userId, Integer sendUserId) {

        Date now = new Date();
        MessageListResponse messageList = new MessageListResponse();
        messageList.setUserId(userId);
        messageList.setSendUserId(sendUserId);
        messageList.setLatelyTime(DateUtils.dateToStr(WebSocketConstants.DATE_FORMAT, now));
        messageList.setMessageDescribe(messageInfo.getContent());
        messageList.setMessageDescribeFormat(messageInfo.getMessageFormat());
        messageList.setUnreadCount(redisService.getCacheObject(userId + WebSocketConstants.MSG_UN_READ + sendUserId));

        BaseUserInfo baseUserInfo = redisService.getCacheObject(Constants.BASE_USER_INFO + sendUserId);
        if (baseUserInfo != null && baseUserInfo.getExtra() != null) {
            messageList.setHeadPortrait(baseUserInfo.getExtra().get(WebSocketConstants.HEAD_PORTRAIT) != null ? baseUserInfo.getExtra().get(WebSocketConstants.HEAD_PORTRAIT).toString() : "");
            messageList.setNickName(baseUserInfo.getExtra().get(WebSocketConstants.NICK_NAME) != null ? baseUserInfo.getExtra().get(WebSocketConstants.NICK_NAME).toString() : "");
        }
        redisService.redisTemplate.opsForZSet().add(WebSocketConstants.MSG_LIST + userId, messageList, now.getTime());


    }
}
