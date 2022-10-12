package com.wxm.msfast.base.websocket.common.enums;

public enum MessageTypeEnum {
    CONNECT("视频匹配连接");
    private String desc;

    MessageTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
