package com.wxm.msfast.nostalgia.common.enums;

public enum EmotionalStatus {
    Single("正在寻觅"),
    Love("恋爱中");

    private String desc;

    EmotionalStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
