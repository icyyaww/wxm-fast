package com.wxm.msfast.base.websocket.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.websocket.common.enums.MessageTypeEnum;
import com.wxm.msfast.base.websocket.common.rest.request.WebSocketMessage;
import com.wxm.msfast.base.websocket.service.IWebSocketService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    //客户端与服务器建立连接的时候触发，
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("与客户端建立连接，通道开启！关联数量为" + ChannelMap.getManager().size());
    }

    //客户端与服务器关闭连接的时候触发，
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 移除用户与channel的关联
        ChannelMap.getManager().entrySet().removeIf(p -> p.getValue().equals(ctx.channel()));
        System.out.println("channel关闭后，关联数量为" + ChannelMap.getManager().size());
    }


    //服务器接受客户端的数据信息，
    @Override
    @Transactional
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
        WebSocketMessage message = null;
        try {
            message = JSON.parseObject(text, WebSocketMessage.class);
        } catch (JSONException e) {
        }
        if (message != null && MessageTypeEnum.CONNECT.equals(message.getMessageType())) {
            if (StringUtils.isNotBlank(message.getInfo())) {
                ChannelMap.put(Integer.valueOf(message.getInfo()), ctx.channel());
                System.out.println("建立连接完成，关联数量为" + ChannelMap.getManager().size());
            }
        } else {
            IWebSocketService webSocketService = SpringBeanUtils.getBean(IWebSocketService.class);
            if (webSocketService != null) {
                webSocketService.read(ctx.channel(), text);
            }
        }

    }
}
