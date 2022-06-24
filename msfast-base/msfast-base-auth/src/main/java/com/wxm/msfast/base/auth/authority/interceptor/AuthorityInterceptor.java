package com.wxm.msfast.base.auth.authority.interceptor;


import cn.hutool.core.util.ObjectUtil;
import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.auth.authority.service.TokenValidService;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.JwtUtils;
import com.wxm.msfast.base.common.utils.SecurityUtils;
import com.wxm.msfast.base.common.utils.ServletUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户安全验证拦截类
 *
 * @author: wuhb
 * @date: 2019/2/25
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Value("${" + ConfigConstants.AUTH_REDIS_ENABLE + ":false}")
    private Boolean redisEnable;

    @Value("${" + ConfigConstants.AUTH_MANY_ONLINE + ":false}")
    private Boolean manyOnline;

    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        AuthIgnore annotation;
        if (handler instanceof HandlerMethod) {
            if (((HandlerMethod) handler).getBean() instanceof BasicErrorController) return true;
            annotation = ((HandlerMethod) handler).getMethodAnnotation(AuthIgnore.class);
        } else {
            return true;
        }

        //忽略校验
        if (ObjectUtil.isNotNull(annotation)) {
            return true;
        }

        String token = SecurityUtils.getToken(request);

        //没有token
        if (StringUtils.isBlank(token)) {
            ServletUtils.responseError(BaseExceptionEnum.NO_LOGIN_EXCEPTION);
            return false;
        }

        if (redisEnable) {
            Long expire = redisService.getExpire(JwtUtils.getUserRedisToken(token));
            if (expire <= 0) {
                ServletUtils.responseError(BaseExceptionEnum.TOKEN_EXPIRED_EXCEPTION);
                return false;
            }

            //不能同时在线
            if (!manyOnline) {
                String onlineToken = redisService.getCacheObject(SecurityConstants.MANY_ONLINE_USER_KEY + JwtUtils.getUserId(token));
                if (StringUtils.isNotBlank(onlineToken)) {
                    if (!onlineToken.equals(JwtUtils.getOnlineUSerToken(token))) {
                        ServletUtils.responseError(BaseExceptionEnum.OTHER_LOGIN_EXCEPTION);
                        return false;
                    }
                }
            }
        }

        TokenService tokenService = SpringUtils.getBean(TokenService.class);
        tokenService.refreshToken(token);

        //校验是否拥有相关权限
        TokenValidService tokenValidService = SpringUtils.getBean(TokenValidService.class);
        if (!Boolean.TRUE.equals(tokenValidService.hasPermission())) {
            ServletUtils.responseError(BaseExceptionEnum.NO_PERMISSION_EXCEPTION);
            return false;
        }

        return true;
    }

}
