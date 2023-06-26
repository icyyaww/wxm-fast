package com.wxm.nostalgia.common.enums;

public enum AuthStatusEnum {

    EXAMINE("审核中"),
    PASS("通过"),
    REFUSE("拒绝");

    private String desc;

    AuthStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
