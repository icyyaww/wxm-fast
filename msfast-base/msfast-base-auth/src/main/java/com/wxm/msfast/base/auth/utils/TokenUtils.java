package com.wxm.msfast.base.auth.utils;

import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.utils.JwtUtils;
import com.wxm.msfast.base.common.utils.SecurityUtils;
import io.jsonwebtoken.Claims;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-09-30 16:24
 **/

public class TokenUtils {

    /**
     * 获取用户id
     *
     * @return 用户ID
     */
    public static Integer getOwnerId() {
        Claims claims = JwtUtils.parseToken(SecurityUtils.getToken());
        if (claims == null) {
            return null;
        }
        return Integer.parseInt(JwtUtils.getValue(claims, SecurityConstants.DETAILS_USER_ID));
    }
}
