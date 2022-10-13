package com.wxm.msfast.community.websocket;

import com.alibaba.fastjson2.JSON;

import com.wxm.msfast.base.websocket.netty.ChannelMap;
import com.wxm.msfast.base.websocket.service.IWebSocketService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import com.wxm.msfast.community.common.enums.MessageTypeEnum;
import com.wxm.msfast.community.common.type.MessageInfo;
import io.netty.channel.Channel;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-12 17:28
 **/

@Service
public class WebSocketServiceImpl implements IWebSocketService {
    @Override
    public void read(Channel channel, String text) {
        MessageInfo message = JSON.parseObject(text, MessageInfo.class);
        if (MessageTypeEnum.MATCHING.equals(message.getMessageType())) {
            if (StringUtils.isNotBlank(message.getInfo())) {
                Channel connect = ChannelMap.get(Integer.parseInt(message.getInfo()));
                if (connect == null) {
                    ChannelUtil.sendText(channel, "未绑定连接");
                } else {
                    //开始匹配
                }
            }
        }
    }
}
