package com.wxm.msfast.community.common.type;

import com.wxm.msfast.community.common.enums.MatchingTypeEnum;
import lombok.Data;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-17 16:41
 **/

@Data
public class MatchingType {

    private MatchingTypeEnum type;

    private Integer userId;
}
