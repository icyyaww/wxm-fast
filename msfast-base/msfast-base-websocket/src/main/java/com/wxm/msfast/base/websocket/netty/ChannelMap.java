package com.wxm.msfast.base.websocket.netty;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @author liuhm
 * @Description: 用户id和channel的关联关系处理
 */
public class ChannelMap {

    private static HashMap<Integer, Channel> manager = new HashMap<>();

    public static void put(Integer key, Channel channel) {
        manager.put(key, channel);
    }

    public static Channel get(Integer key) {
        return manager.get(key);
    }

    public static HashMap<Integer, Channel> getManager() {
        return manager;
    }
}
