package com.wxm.msfast.base.auth.service;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.ObjectUtil;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.AuthorityUserResponse;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.constant.TokenConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.*;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
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

    @Value("${spring.redis.open:false}")
    private Boolean redisOpen;

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

        //保存token信息到前台中
        ServletUtils.setCookie(TokenConstants.AUTHENTICATION, loginUserResponse.getToken());
        return loginUserResponse;
    }

    @Override
    public void logout() {
        if (redisOpen) {
            String token = SecurityUtils.getToken();
            if (StringUtils.isNotBlank(token)) {
                redisService.deleteObject(SecurityConstants.REDIS_USER_KEY + JwtUtils.getUserId(token));
            }
        }

        ServletUtils.removeCookie(TokenConstants.AUTHENTICATION);
        //用户退出登陆
        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);
        authorityService.logout();
    }

    @Override
    public void refreshToken(String token) {

        Claims claims = JwtUtils.parseToken(token);
        Date expiration = claims.getExpiration();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TokenConstants.REFRESH_TIME);
        if (calendar.getTime().compareTo(expiration) > 0) {
            //刷新token
            LoginUser loginUser = claims.get(SecurityConstants.LOGIN_USER, LoginUser.class);
            String refreshToken = createToken(loginUser);
            //保存token信息到前台中
            ServletUtils.setCookie(TokenConstants.AUTHENTICATION, refreshToken);
        }
    }

    private String createToken(LoginUser loginUser) {
        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.LOGIN_USER, loginUser);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, loginUser.getId());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, TokenConstants.EXPIRATION);
        if (redisOpen) {
            String redistToken = IdUtils.fastSimpleUUID();
            claimsMap.put(SecurityConstants.REDIS_TOKEN, redistToken);
            redisService.setCacheObject(SecurityConstants.REDIS_USER_KEY + loginUser.getId(), redistToken, Long.parseLong(String.valueOf(TokenConstants.EXPIRATION)), TimeUnit.MINUTES);
        }
        return JwtUtils.createToken(claimsMap, calendar.getTime());
    }
}
