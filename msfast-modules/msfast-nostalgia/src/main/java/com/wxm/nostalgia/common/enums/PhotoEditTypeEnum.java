package com.wxm.nostalgia.common.enums;

public enum PhotoEditTypeEnum {
    REPLACE("替换"),
    DELETE("删除"),
    ADD("新增");
    private String desc;

    PhotoEditTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
