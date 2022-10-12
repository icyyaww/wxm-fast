package com.wxm.msfast.base.websocket.common.type;


import com.wxm.msfast.base.websocket.common.enums.MessageTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-12 16:16
 **/

@Data
public class MessageType {

    private MessageTypeEnum messageTypeEnum;

    private String info;
}
