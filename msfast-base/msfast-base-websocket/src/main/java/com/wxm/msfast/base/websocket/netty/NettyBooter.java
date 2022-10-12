package com.wxm.msfast.base.websocket.netty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @ClassName：NettyBooter
 * @Description: 初始化程序后加载此方法
 * @Author: liuhm
 * @Date: 2020/3/9 0009 上午 10:49
 */
@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    WSServer wsServer;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            wsServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
