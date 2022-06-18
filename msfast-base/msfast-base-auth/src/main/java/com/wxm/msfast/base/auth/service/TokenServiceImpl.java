package com.wxm.msfast.base.auth.service;

import cn.hutool.core.util.ObjectUtil;
import com.wxm.msfast.api.auth.feign.AuthFeignService;
import com.wxm.msfast.base.auth.authority.service.AuthorityService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.AuthorityUserResponse;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.constant.SecurityConstants;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.JwtUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 11:26
 **/

@Service
public class TokenServiceImpl implements TokenService {


    @Override
    public LoginUserResponse login(LoginRequest request) {

        LoginUserResponse loginUserResponse = new LoginUserResponse();

        //用户登陆业务校验
        AuthorityService authorityService = SpringUtils.getBean(AuthorityService.class);

        LoginUser loginUser = authorityService.login(request);
        if (ObjectUtil.isNull(loginUser) || !Boolean.TRUE.equals(loginUser.getSuccess())) {
            //登陆失败
            throw new JrsfException(BaseExceptionEnum.LOGIN_FAIL_EXCEPTION);
        }
        AuthorityUserResponse authorityUserResponse = new AuthorityUserResponse();
        BeanUtils.copyProperties(loginUser, authorityUserResponse);
        loginUserResponse.setAuthorityUserResponse(authorityUserResponse);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, authorityUserResponse.getUsername());
        loginUserResponse.setToken(JwtUtils.createToken(claimsMap));

        return loginUserResponse;
    }
}
