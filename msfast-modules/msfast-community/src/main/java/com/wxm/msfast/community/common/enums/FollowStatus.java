package com.wxm.msfast.community.common.enums;

public enum FollowStatus {
    FOLLOW("我关注"),
    FANS("关注我"),
    FRIEND("互相关注");
    private String desc;

    FollowStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}