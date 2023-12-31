package com.wxm.base.common.utils;

import cn.hutool.core.map.MapUtil;
import com.wxm.base.common.entity.LoginUser;
import com.wxm.base.common.constant.SecurityConstants;
import io.jsonwebtoken.Claims;

import java.util.Map;

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

    /**
     * @Description: 获取登陆用户基础信息
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午3:17
     */
    public static <T> LoginUser<T> info(Class<T> cls) {
        //todo 增加过期校验
        Claims claims = JwtUtils.parseToken(SecurityUtils.getToken());
        if (claims == null) {
            return null;
        }
        Map map = claims.get(SecurityConstants.LOGIN_USER, Map.class);
        LoginUser loginUser = new LoginUser();
        loginUser.setId((Integer) map.get("id"));
        T t = MapUtil.get(map, "info", cls);
        loginUser.setInfo(t);
        return loginUser;
    }
}
