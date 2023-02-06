package com.wxm.msfast.base.websocket.common.rest.response;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-06 14:46
 **/

@Data
public class MessageListResponse {

    private Integer userId;

    private Integer sendUserId;

    private String latelyTime;

    private Integer unreadCount;

    private String messageDescribe;

    private String messageDescribeFormat;

    private String nickName;

    private String headPortrait;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageListResponse that = (MessageListResponse) o;
        return userId.equals(that.userId) &&
                sendUserId.equals(that.sendUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, sendUserId);
    }
}
