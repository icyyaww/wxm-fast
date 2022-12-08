package com.wxm.msfast.nostalgia.authority;

import com.wxm.msfast.base.auth.authority.service.IAuthorityServiceImpl;
import com.wxm.msfast.base.auth.authority.service.WxAppletService;
import com.wxm.msfast.base.auth.common.rest.request.LoginRequest;
import com.wxm.msfast.base.auth.common.rest.response.WxAppletOpenResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.common.enums.FrUserStatusEnum;
import com.wxm.msfast.base.common.enums.UserExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.file.service.MsfFileService;
import com.wxm.msfast.nostalgia.common.enums.AuthStatusEnum;
import com.wxm.msfast.nostalgia.common.enums.UserTypeEnum;
import com.wxm.msfast.nostalgia.common.request.fruser.AppletRegisterRequest;
import com.wxm.msfast.nostalgia.common.response.fruser.LoginResponse;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
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
public class AuthorityServiceImpl extends IAuthorityServiceImpl<LoginRequest, AppletRegisterRequest> {

    @Autowired
    MsfFileService fileService;

    @Autowired
    FrUserService frUserService;

    @Autowired
    WxAppletService wxAppletService;

    /*
     * @Author wanglei
     * @Description  微信小程序登录
     * @Date 16:49 2022/12/5
     * @Param
     * @return
     **/
    @Override
    public LoginUser login(LoginRequest loginRequest) {

        LoginUser loginUser = new LoginUser();
        FrUserEntity frUserEntity = this.frUserService.getFrUserByOpenId(loginRequest.getOpenId());
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

    @Override
    @Transactional
    public void wxAppletRegister(AppletRegisterRequest request) {

        FrUserEntity frUserEntity = new FrUserEntity();
        BeanUtils.copyProperties(request, frUserEntity);
        Integer age = DateUtils.getAgeByBirth(request.getBirthday());
        if (age < 16) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("未满16周岁无法注册");
        }
        if (age > 100) {
            throw new JrsfException(UserExceptionEnum.AGE_NOT_RANGE_EXCEPTION).setMsg("超过100周岁无法注册");
        }
        if (this.frUserService.countByOpenId(request.getOpenId()) > 0l) {
            throw new JrsfException(UserExceptionEnum.USER_EXIST_EXCEPTION);
        }

        frUserEntity.setStatus(FrUserStatusEnum.ENABLE);
        frUserEntity.setAuthStatus(AuthStatusEnum.EXAMINE);
        frUserEntity.setUserType(UserTypeEnum.Normal);
        this.frUserService.save(frUserEntity);

        //保存头像
        fileService.changeTempFile(request.getHeadPortrait());

    }
}
