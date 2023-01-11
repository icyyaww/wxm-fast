package com.wxm.msfast.base.websocket.common.enums;

public enum MessageTypeEnum {
    CONNECT("用户连接"),
    IM_MESSAGE("im消息"),
    ALIVE("心跳");
    private String desc;

    MessageTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
