package com.wxm.msfast.community.authority;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.authority.service.AuthorityServiceImpl;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.community.common.exception.UserExceptionEnum;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserRegisterRequest;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-16 18:05
 **/
@Service
public class UserAuthorityServiceImpl extends AuthorityServiceImpl<UserLoginRequest, UserRegisterRequest> {

    @Autowired
    FrUserService frUserService;

    @Override
    public void register(UserRegisterRequest registerRequest) {
        super.register(registerRequest);
        System.out.println(registerRequest);
    }

    @Override
    public void sendSmsBefore(SendSmsRequest sendSmsRequest) {

        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, sendSmsRequest.getPhone());
        Long count = frUserService.count(frUserEntityWrapper);
        if (MessageType.REGISTER.equals(sendSmsRequest.getMessageType())) {
            //注册
            if (count > 0l) {
                throw new JrsfException(UserExceptionEnum.USER_EXIST_EXCEPTION);
            }
        }

        if (MessageType.RESETPWD.equals(sendSmsRequest.getMessageType()) || MessageType.LOGIN.equals(sendSmsRequest.getMessageType())) {
            //重置密码
            if (count == 0l) {
                throw new JrsfException(UserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
            }
        }
    }
}
