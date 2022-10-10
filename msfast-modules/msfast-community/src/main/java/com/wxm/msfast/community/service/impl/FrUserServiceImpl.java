package com.wxm.msfast.community.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.community.common.enums.SysConfigEnum;
import com.wxm.msfast.community.common.exception.UserExceptionEnum;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.response.user.DynamicUserResponse;
import com.wxm.msfast.community.common.rest.response.user.LoginResponse;
import com.wxm.msfast.community.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.base.common.web.page.Query;

import com.wxm.msfast.community.dao.FrUserDao;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrUserService;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    TokenService tokenService;

    @Autowired
    SysConfigService sysConfigService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<FrUserEntity> page = this.page(
                new Query<FrUserEntity>().getPage(params),
                new QueryWrapper<FrUserEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * @Description: 根据手机号查询数量
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/9/29 下午2:53
     */
    @Override
    public Long countByPhone(String phone) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, phone);
        return count(frUserEntityWrapper);
    }

    @Override
    public FrUserEntity getFrUserByPhone(String phone) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getPhone, phone);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public LoginUserResponse smsLogin(SmsLoginRequest request) {

        FrUserEntity frUserEntity = getFrUserByPhone(request.getPhone());
        if (frUserEntity == null) {
            throw new JrsfException(UserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }
        CheckSmsRequest checkSmsRequest = new CheckSmsRequest();
        checkSmsRequest.setMessageType(MessageType.LOGIN);
        BeanUtils.copyProperties(request, checkSmsRequest);
        tokenService.checkSms(checkSmsRequest);

        UserLoginRequest userLoginRequest = new UserLoginRequest();


        userLoginRequest.setUsername(request.getPhone());
        userLoginRequest.setPassword(frUserEntity.getPassword());

        return tokenService.login(userLoginRequest);
    }

    @Override
    public List<DynamicUserResponse> getDynamicUser() {
        Map<String, Object> param = new HashMap<>();
        Integer loginUserId = TokenUtils.getOwnerId();
        if (loginUserId != null) {
            FrUserEntity frUserEntity = getById(loginUserId);
            if (frUserEntity != null) {
                param.put("id", frUserEntity.getId());
                param.put("gender", frUserEntity.getGender().name());
            }
        }

        return this.baseMapper.getDynamicUser(param);
    }

    @Override
    public LoginResponse info() {
        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        return loginUser.getInfo();
    }

    /**
     * @Description: 获取匹配提示信息
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午4:40
     */
    @Override
    public List<String> message() {
        String value = sysConfigService.getValueByCode(SysConfigEnum.video_matching_tips.name());
        if (StringUtils.isNotBlank(value)) {
            return JSON.parseArray(value, String.class);
        }
        return null;
    }

}
