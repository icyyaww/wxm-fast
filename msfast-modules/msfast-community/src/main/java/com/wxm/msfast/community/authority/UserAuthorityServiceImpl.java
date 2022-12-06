package com.wxm.msfast.community.authority;

import com.wxm.msfast.base.auth.authority.service.AuthorityServiceImpl;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.SendSmsRequest;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.enums.FrUserStatusEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.file.annotation.FileSaveService;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.community.common.exception.UserExceptionEnum;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserRegisterRequest;
import com.wxm.msfast.community.common.rest.response.user.LoginResponse;
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

    @Autowired
    MsfFileService fileService;

    @Override
    @Transactional
    @FileSaveService
    public void register(UserRegisterRequest registerRequest) {
        FrUserEntity frUserEntity = new FrUserEntity();
        BeanUtils.copyProperties(registerRequest, frUserEntity);
        Integer age = DateUtils.getAgeByBirth(registerRequest.getBirthday());
        if (age < 16) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("未满16周岁无法注册");
        }
        if (age > 100) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("超过100周岁无法注册");
        }

        if (this.frUserService.countByPhone(registerRequest.getPhone()) > 0l) {
            throw new JrsfException(UserExceptionEnum.USER_EXIST_EXCEPTION);
        }

        frUserEntity.setStatus(FrUserStatusEnum.ENABLE);
        this.frUserService.save(frUserEntity);

        //保存头像
        fileService.changeTempFile(registerRequest.getHeadPortrait());
    }

    @Override
    public void sendSmsBefore(SendSmsRequest sendSmsRequest) {

        Long count = this.frUserService.countByPhone(sendSmsRequest.getPhone());
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

    @Override
    public LoginUser login(UserLoginRequest loginRequest) {

        LoginUser loginUser = new LoginUser();
        FrUserEntity frUserEntity = this.frUserService.getFrUserByPhone(loginRequest.getUsername());
        if (frUserEntity == null) {
            throw new JrsfException(UserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }

        if (FrUserStatusEnum.DISABLE.equals(frUserEntity.getStatus())) {
            throw new JrsfException(UserExceptionEnum.USER_STATUS_ERROR_EXCEPTION);
        }

        loginUser.setId(frUserEntity.getId());
        LoginResponse loginResponse = new LoginResponse();
        BeanUtils.copyProperties(frUserEntity, loginResponse);
        loginUser.setInfo(loginResponse);
        return loginUser;
    }
}
