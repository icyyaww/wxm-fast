package com.wxm.msfast.community.websocket.matching;

import io.netty.channel.Channel;

import java.util.HashMap;

/**
 * @author liuhm
 * @Description: 用户id和channel的关联关系处理
 */
public class UserChannelMap {

	private static HashMap<Integer, Channel> manager = new HashMap<>();

	public static void put(Integer senderId, Channel channel) {
		manager.put(senderId, channel);
	}

	public static Channel get(Integer senderId) {
		return manager.get(senderId);
	}
	public static HashMap<Integer, Channel> getManager() {
		return manager;
	}
}
