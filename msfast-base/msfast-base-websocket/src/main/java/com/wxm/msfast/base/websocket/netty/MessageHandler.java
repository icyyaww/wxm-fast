package com.wxm.msfast.base.websocket.netty;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONException;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.websocket.common.enums.MessageTypeEnum;
import com.wxm.msfast.base.websocket.common.rest.request.BaseMessageInfo;
import com.wxm.msfast.base.websocket.common.rest.request.WebSocketMessage;
import com.wxm.msfast.base.websocket.service.IWebSocketService;
import com.wxm.msfast.base.websocket.service.IMessageService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.net.SocketAddress;

@Component
public class MessageHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    //客户端与服务器建立连接的时候触发，
    @Override
    public void channelActive(ChannelHandlerContext ctx) {

        SocketAddress socketAddress = ctx.channel().remoteAddress();
        String ip = socketAddress.toString().replaceAll("/", "");
        ip = ip.substring(0, ip.indexOf(":"));

        //将相同ip的管道关闭 防止资源占用
        if (ConfigConstants.ONLY_ONE()) {
            Channel channel = ChannelMap.getOnline().get(ip);
            if (channel != null) {
                channel.close();
            }
        }

        ChannelMap.getOnline().put(ip, ctx.channel());
        System.out.println("与客户端建立连接, 客户端ip:" + ip + "，通道开启！ 用户连接数量：" + ChannelMap.getManager().size() + " 在线客户端数：" + ChannelMap.getOnline().size());

        IWebSocketService webSocketService = SpringBeanUtils.getBean(IWebSocketService.class);
        if (webSocketService != null) {
            webSocketService.connect(ctx.channel());
        }
    }

    //客户端与服务器关闭连接的时候触发，
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {

        IWebSocketService webSocketService = SpringBeanUtils.getBean(IWebSocketService.class);
        if (webSocketService != null) {
            webSocketService.close(ctx.channel());
        }
        // 移除用户与channel的关联
        ChannelMap.getManager().entrySet().removeIf(p -> p.getValue().equals(ctx.channel()));
        ChannelMap.getOnline().entrySet().removeIf(p -> p.getValue().equals(ctx.channel()));
        System.out.println("channel关闭监听，用户连接数量：" + ChannelMap.getManager().size() + "在线客户端数：" + ChannelMap.getOnline().size());
    }


    //服务器接受客户端的数据信息，
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
        WebSocketMessage message = null;
        ChannelUtil channelUtil = SpringUtils.getBean(ChannelUtil.class);
        try {
            message = JSON.parseObject(text, WebSocketMessage.class);
        } catch (JSONException e) {
            channelUtil.sendText(ctx.channel(), "消息格式错误 例：{\"messageType\":\"IM_MESSAGE\",\"info\":\"{'sendUserId':'1','acceptUserId':'2','content':'内容','messageFormat':'mage/text/emote/voice/video','sendName':'名字','sendPortrait':'头像'}\"}");
        }

        if (message != null && MessageTypeEnum.CONNECT.equals(message.getMessageType())) {
            if (StringUtils.isNotBlank(message.getInfo())) {
                ChannelMap.put(Integer.valueOf(message.getInfo()), ctx.channel());
                System.out.println("建立连接完成，关联数量为" + ChannelMap.getManager().size());
            }
        } else if (message != null && MessageTypeEnum.ALIVE.equals(message.getMessageType())) {
            //心跳检测
            return;
        } else if (message != null && MessageTypeEnum.IM_MESSAGE.equals(message.getMessageType())) {
            try {
                BaseMessageInfo messageInfo = JSON.parseObject(message.getInfo(), BaseMessageInfo.class);
                IMessageService IMessageService = SpringUtils.getBean(IMessageService.class);
                IMessageService.send(messageInfo);
            } catch (JSONException e) {
                channelUtil.sendText(ctx.channel(), "info格式错误 例：{'sendUserId':'1','acceptUserId':'2','content':'内容','messageFormat':'mage/text/emote/voice/video','sendName':'名字','sendPortrait':'头像'}");
            }
        } else {
            IWebSocketService webSocketService = SpringBeanUtils.getBean(IWebSocketService.class);
            if (webSocketService != null) {
                webSocketService.read(ctx.channel(), text);
            }
        }

    }
}
