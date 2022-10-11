package com.wxm.msfast.community.websocket.matching;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    private static ChatHandler webSocketServerHandler;

    @PostConstruct
    public void init() {
        webSocketServerHandler = this;
    }

    //客户端与服务器建立连接的时候触发，
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //添加到channelGroup通道组
        channelGroup.add(ctx.channel());
        System.out.println("与客户端建立连接，通道开启！此时连接人数：" + channelGroup.size());
    }

    //客户端与服务器关闭连接的时候触发，
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        System.out.println("channel关闭前，users的数量为：" + ChatHandler.channelGroup.size() + "; 关联数量为" + UserChannelMap.getManager().size());
        //移除管道
        channelGroup.remove(ctx.channel());
        // 移除用户与channel的关联
        UserChannelMap.getManager().entrySet().removeIf(p -> p.getValue().equals(ctx.channel()));
        System.out.println("channel关闭后，users的数量为：" + ChatHandler.channelGroup.size() + "; 关联数量为" + UserChannelMap.getManager().size());
    }


    //服务器接受客户端的数据信息，
    @Override
    @Transactional
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
        String text = msg.text();
    }
}
