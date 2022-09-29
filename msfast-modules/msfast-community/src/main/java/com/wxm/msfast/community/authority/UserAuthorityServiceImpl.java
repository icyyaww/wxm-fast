package com.wxm.msfast.community.authority;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.authority.service.AuthorityServiceImpl;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.common.enums.BaseExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.community.common.exception.UserExceptionEnum;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserRegisterRequest;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public void register(UserRegisterRequest registerRequest) {

        FrUserEntity frUserEntity = new FrUserEntity();
        BeanUtils.copyProperties(registerRequest, frUserEntity);
        Integer age = DateUtils.getAgeByBirth(registerRequest.getBirthday());
        if (age < 16) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setData("未满16周岁无法注册");
        }
        if (age > 100) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setData("超过100周岁无法注册");
        }

        if (this.frUserService.countByPhone(registerRequest.getPhone()) > 0l) {
            throw new JrsfException(UserExceptionEnum.USER_EXIST_EXCEPTION);
        }

        this.frUserService.save(frUserEntity);
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
