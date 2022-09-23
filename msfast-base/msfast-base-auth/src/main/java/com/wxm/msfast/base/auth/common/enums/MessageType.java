package com.wxm.msfast.base.auth.common.enums;

public enum MessageType {

    LOGIN("登陆"),
    REGISTER("注册");
    private String desc;

    MessageType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
