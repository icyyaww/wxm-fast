package com.wxm.nostalgia.common.enums;

public enum MaritalStatusEnum {
    Unmarried("未婚"),
    Divorce("离异"),
    Widow("丧偶");
    private String desc;

    MaritalStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
