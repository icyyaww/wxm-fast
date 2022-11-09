package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.auth.common.rest.response.LoginUserResponse;
import com.wxm.msfast.base.common.utils.PageUtils;
import com.wxm.msfast.community.common.rest.request.user.SmsLoginRequest;
import com.wxm.msfast.community.common.rest.response.user.DynamicUserResponse;
import com.wxm.msfast.community.common.rest.response.user.LoginResponse;
import com.wxm.msfast.community.common.rest.response.user.PersonalCenterResponse;
import com.wxm.msfast.community.entity.FrUserEntity;

import java.util.List;
import java.util.Map;

/**
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-09-22 15:46:53
 */
public interface FrUserService extends IService<FrUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Long countByPhone(String phone);

    FrUserEntity getFrUserByPhone(String phone);

    LoginUserResponse smsLogin(SmsLoginRequest request);

    List<DynamicUserResponse> getDynamicUser();

    /**
     * @Description: 获取当前登陆用户信息
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午2:18
     */
    LoginResponse info();

    /**
     * @Description: 获取匹配提示信息
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/10/10 下午4:40
     */
    List<String> message();

    void startMatching(Integer userId);

    PersonalCenterResponse personalCenter();
}

