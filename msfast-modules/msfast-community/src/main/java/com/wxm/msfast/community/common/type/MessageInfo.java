package com.wxm.msfast.community.common.type;


import com.wxm.msfast.community.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-13 17:42
 **/

@Data
public class MessageInfo {

    private MessageTypeEnum messageType;

    private String info;
}
