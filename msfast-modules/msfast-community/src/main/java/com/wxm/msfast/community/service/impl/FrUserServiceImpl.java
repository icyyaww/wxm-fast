package com.wxm.msfast.community.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxm.msfast.base.auth.common.enums.MessageType;
import com.wxm.msfast.base.auth.common.rest.request.CheckSmsRequest;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.auth.service.TokenService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.enums.BaseUserExceptionEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.service.RedisService;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.file.annotation.FileSaveService;
import com.wxm.msfast.community.common.constant.Constants;
import com.wxm.msfast.community.common.constant.PropertiesConstants;
import com.wxm.msfast.community.common.enums.SysConfigEnum;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import com.wxm.msfast.community.common.rest.request.user.UserLoginRequest;
import com.wxm.msfast.community.common.rest.response.user.*;
import com.wxm.msfast.community.dao.FrUserDao;
import com.wxm.msfast.community.entity.FrUserEntity;
import com.wxm.msfast.community.service.FrBlogService;
import com.wxm.msfast.community.service.FrUserFollowService;
import com.wxm.msfast.community.service.FrUserService;
import com.wxm.msfast.community.service.SysConfigService;
import com.wxm.msfast.community.utils.TLSSigAPIv2;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    TokenService tokenService;

    @Autowired
    SysConfigService sysConfigService;

    @Autowired
    FrBlogService frBlogService;

    @Autowired
    FrUserFollowService frUserFollowService;

    @Resource
    private RedisService redisService;


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
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
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

    @Override
    public void startMatching() {
        redisService.deleteObject(Constants.MATCHING_SUCCESS + TokenUtils.getOwnerId());
    }

    @Override
    public void endMatching() {
        redisService.deleteObject(Constants.MATCHING + TokenUtils.getOwnerId());
    }

    @Override
    @FileSaveService
    public PersonalCenterResponse personalCenter() {

        Integer loginUserId = TokenUtils.getOwnerId();
        FrUserEntity frUserEntity = this.baseMapper.selectById(loginUserId);
        if (frUserEntity == null) {
            throw new JrsfException(BaseUserExceptionEnum.USER_NOT_EXIST_EXCEPTION);
        }
        PersonalCenterResponse personalCenterResponse = new PersonalCenterResponse();
        BeanUtils.copyProperties(frUserEntity, personalCenterResponse);

        personalCenterResponse.setBlogCount(this.frBlogService.userBlogCount());
        personalCenterResponse.setFollowCount(this.frUserFollowService.followUserCount());
        personalCenterResponse.setFansCount(this.frUserFollowService.fansCount());

        personalCenterResponse.setBlog(frBlogService.getPersonalBlogImage());
        return personalCenterResponse;
    }

    @Override
    public PageResult<FollowPageResponse> followPage(Integer pageIndex, Integer pageSize) {

        Page<FollowPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getFollowPage(TokenUtils.getOwnerId());
        PageResult<FollowPageResponse> result = new PageResult<>(page);

        return result;
    }

    @Override
    public PageResult<FollowPageResponse> fansPage(Integer pageIndex, Integer pageSize) {

        Page<FollowPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getFansPage(TokenUtils.getOwnerId());
        PageResult<FollowPageResponse> result = new PageResult<>(page);

        return result;

    }

    @Override
    public void cancelFollow(Integer id) {

    }

    @Override
    public void followUser(Integer id) {

    }

    @Override
    public void removeFans(Integer id) {

    }

    @Override
    public TuiCallKitResponse tuiCallKit() {
        TuiCallKitResponse tuiCallKitResponse = new TuiCallKitResponse();
        tuiCallKitResponse.setSdkAppId(PropertiesConstants.SDK_APP_ID());
        tuiCallKitResponse.setUserId(TokenUtils.getOwnerId().toString());
        TLSSigAPIv2 tlsSigAPIv2 = new TLSSigAPIv2(tuiCallKitResponse.getSdkAppId(), PropertiesConstants.SECRET_KEY());
        tuiCallKitResponse.setUserSig(tlsSigAPIv2.genUserSig(TokenUtils.getOwnerId().toString(), 86400));

        LoginResponse loginResponse = TokenUtils.info(LoginResponse.class).getInfo();
        tuiCallKitResponse.setNickName(loginResponse.getNickName());
        tuiCallKitResponse.setAvatar(loginResponse.getHeadPortrait());
        tuiCallKitResponse.setCallingBell(sysConfigService.getValueByCode(SysConfigEnum.home_bgm.name()));
        return tuiCallKitResponse;
    }

}
