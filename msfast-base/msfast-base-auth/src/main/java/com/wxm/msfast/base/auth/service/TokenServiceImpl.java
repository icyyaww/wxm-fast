package com.wxm.msfast.base.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.AuthorityUserResponse;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.constant.ConfigConstants;
import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.*;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 11:26
 **/
@RefreshScope
@Service
public class TokenServiceImpl implements TokenService {

    @Resource
    private RedisService redisService;

    @Override
    public LoginUserResponse login(LoginRequest request) {

        LoginUserResponse loginUserResponse = new LoginUserResponse();

        //用户登陆业务校验
        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);

        LoginUser loginUser = authorityService.login(request);
        if (ObjectUtil.isNull(loginUser) || ObjectUtil.isNull(loginUser.getId())) {
            //登陆失败
            throw new JrsfException(BaseExceptionEnum.LOGIN_FAIL_EXCEPTION);
        }
        AuthorityUserResponse authorityUserResponse = new AuthorityUserResponse();
        BeanUtils.copyProperties(loginUser, authorityUserResponse);
        loginUserResponse.setAuthorityUserResponse(authorityUserResponse);

        loginUserResponse.setToken(createToken(loginUser));

        return loginUserResponse;
    }

    @Override
    public void logout() {
        if (ConfigConstants.AUTH_REDIS_ENABLE()) {
            String token = SecurityUtils.getToken();
            if (StringUtils.isNotBlank(token)) {
                redisService.deleteObject(JwtUtils.getUserRedisToken(token));
                if (!ConfigConstants.AUTH_MANY_ONLINE()) {
                    redisService.deleteObject(SecurityConstants.MANY_ONLINE_USER_KEY + JwtUtils.getUserId(token));
                }
            }
        }

        //用户退出登陆
        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);
        authorityService.logout();
    }

    @Override
    public void refreshToken(String token) {

        String redisToken = JwtUtils.getUserRedisToken(token);

        if (ConfigConstants.AUTH_REDIS_ENABLE()) {
            Long expire = redisService.getExpire(redisToken, TimeUnit.MINUTES);
            if (expire.compareTo(0l) > 0 && expire.compareTo(ConfigConstants.REFRESH()) <=0) {
                redisService.setCacheObject(JwtUtils.getUserRedisToken(token), JwtUtils.getUserId(token), ConfigConstants.EXPIRATION(), TimeUnit.MINUTES);
                if (Boolean.FALSE.equals(ConfigConstants.AUTH_MANY_ONLINE())) {
                    redisService.setCacheObject(SecurityConstants.MANY_ONLINE_USER_KEY + JwtUtils.getUserId(token), JwtUtils.getUserRedisToken(token), ConfigConstants.EXPIRATION(), TimeUnit.MINUTES);
                }
            }
        }

    }

    private String createToken(LoginUser loginUser) {

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.LOGIN_USER, loginUser);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getId());

        if (ConfigConstants.AUTH_REDIS_ENABLE()) {
            String redisToken = IdUtils.fastSimpleUUID();
            claimsMap.put(SecurityConstants.REDIS_TOKEN, redisToken);
            redisService.setCacheObject(redisToken, loginUser.getId(), ConfigConstants.EXPIRATION(), TimeUnit.MINUTES);
            if (Boolean.FALSE.equals(ConfigConstants.AUTH_MANY_ONLINE())) {
                redisService.setCacheObject(SecurityConstants.MANY_ONLINE_USER_KEY + loginUser.getId(), redisToken, ConfigConstants.EXPIRATION(), TimeUnit.MINUTES);
            }
        }
        return JwtUtils.createToken(claimsMap);
    }
}
