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

        loginUserResponse.setToken(createToken(loginUser, null));

        //保存token信息到前台中
        ServletUtils.setCookie(ConfigConstants.AUTHENTICATION(), loginUserResponse.getToken());
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

        ServletUtils.removeCookie(ConfigConstants.AUTHENTICATION());
        //用户退出登陆
        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);
        authorityService.logout();
    }

    @Override
    public void refreshToken(String token) {

        Claims claims = JwtUtils.parseToken(token);
        Date expiration = claims.getExpiration();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ConfigConstants.REFRESH());
        if (calendar.getTime().compareTo(expiration) > 0) {
            //刷新token
            Map<String, Object> map = claims.get(SecurityConstants.LOGIN_USER, Map.class);
            LoginUser loginUser = new LoginUser();
            MapUtils.copyPropertiesInclude(map, loginUser);
            String refreshToken = createToken(loginUser, token);
            //保存token信息到前台中
            ServletUtils.setCookie(ConfigConstants.AUTHENTICATION(), refreshToken);
            redisService.deleteObject(JwtUtils.getUserRedisToken(token));
        }
    }

    private String createToken(LoginUser loginUser, String token) {
        // Jwt存储信息

        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.LOGIN_USER, loginUser);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, ConfigConstants.EXPIRATION());
        if (ConfigConstants.AUTH_REDIS_ENABLE()) {
            String redisToken = IdUtils.fastSimpleUUID();
            claimsMap.put(SecurityConstants.REDIS_TOKEN, redisToken);
            redisService.setCacheObject(redisToken, loginUser.getId(), Long.parseLong(String.valueOf(ConfigConstants.EXPIRATION())), TimeUnit.MINUTES);
            if (Boolean.FALSE.equals(ConfigConstants.AUTH_MANY_ONLINE())) {
                String onlineToken = "";
                if (StringUtils.isBlank(token)) {
                    //登陆
                    onlineToken = redisToken;
                    claimsMap.put(SecurityConstants.MANY_ONLINE_USER_TOKEN, onlineToken);
                } else {
                    onlineToken = JwtUtils.getOnlineUSerToken(token);
                }
                redisService.setCacheObject(SecurityConstants.MANY_ONLINE_USER_KEY + loginUser.getId(), onlineToken, Long.parseLong(String.valueOf(ConfigConstants.EXPIRATION())), TimeUnit.MINUTES);
            }
        }
        return JwtUtils.createToken(claimsMap, calendar.getTime());
    }
}
