package com.wxm.msfast.nostalgia.common.enums;

public enum MatchingStatusEnum {
    SUCCESS("匹配成功"),
    LIKE_ME("喜欢我的"),
    LIKE("我喜欢的");
    private String desc;

    MatchingStatusEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
