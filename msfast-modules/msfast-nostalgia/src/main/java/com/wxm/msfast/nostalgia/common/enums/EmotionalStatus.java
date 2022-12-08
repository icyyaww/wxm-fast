package com.wxm.msfast.nostalgia.common.enums;

public enum EmotionalStatus {
    Single("单身"),
    Love("恋爱");

    private String desc;

    EmotionalStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
