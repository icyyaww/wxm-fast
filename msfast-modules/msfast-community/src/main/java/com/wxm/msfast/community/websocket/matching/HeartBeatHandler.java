package com.wxm.msfast.community.websocket.matching;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName：HeartBeatHandler
 * @Description: 心跳检测
 * @Author: liuhm
 * @Date: 2020/3/13 0013 上午 11:46
 */

public class HeartBeatHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        // 判断evt是否是IdleStateEvent（用于触发用户事件，包含 读空闲/写空闲/读写空闲 ）
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;        // 强制类型转换

            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("进入读空闲...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("进入写空闲...");
            } else if (event.state() == IdleState.ALL_IDLE) {

                System.out.println("channel关闭前，users的数量为：" + ChatHandler.channelGroup.size() + "; 关联数量为" + UserChannelRel.getManager().size());

                Channel channel = ctx.channel();
                // 关闭无用的channel，以防资源浪费
                channel.close();
                // 移除用户与channel的关联
                UserChannelRel.getManager().entrySet().removeIf(p->p.getValue().equals(ctx.channel()));
                System.out.println("channel关闭后，users的数量为：" + ChatHandler.channelGroup.size() + "; 关联数量为" + UserChannelRel.getManager().size());
            }
        }

    }

}
