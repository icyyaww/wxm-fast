package com.wxm.msfast.base.auth.authority.interceptor;


import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.JWTSignerUtil;
import com.alibaba.fastjson2.JSON;
import com.wxm.msfast.base.auth.annotation.AuthIgnore;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.authority.service.TokenValidService;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.constant.TokenConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.JwtUtils;
import com.wxm.msfast.base.common.utils.SecurityUtils;
import com.wxm.msfast.base.common.utils.ServletUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.base.common.web.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户安全验证拦截类
 *
 * @author: wuhb
 * @date: 2019/2/25
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor {

    @Value("${spring.redis.open:false}")
    private Boolean redisOpen;

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

        if (redisOpen) {
            Long expire = redisService.getExpire(JwtUtils.getUserRedisToken(token));
            if (expire <= 0) {
                ServletUtils.responseError(BaseExceptionEnum.TOKEN_EXPIRED_EXCEPTION);
                return false;
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
