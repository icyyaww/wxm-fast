package com.wxm.msfast.nostalgia.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.entity.LoginUser;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.nostalgia.common.constant.Constants;
import com.wxm.msfast.nostalgia.common.enums.SysConfigCodeEnum;
import com.wxm.msfast.nostalgia.common.exception.UserExceptionEnum;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.LoginResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendConfigResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.entity.RecommendConfigEntity;
import com.wxm.msfast.nostalgia.service.RecommendConfigService;
import com.wxm.msfast.nostalgia.service.UserMatchingService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.FrUserDao;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Service("frUserService")
public class FrUserServiceImpl extends ServiceImpl<FrUserDao, FrUserEntity> implements FrUserService {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    UserMatchingService userMatchingService;

    @Autowired
    RecommendConfigService recommendConfigService;

    @Override
    public Long countByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return count(frUserEntityWrapper);
    }

    @Override
    public FrUserEntity getFrUserByOpenId(String openId) {
        Wrapper<FrUserEntity> frUserEntityWrapper = new QueryWrapper<FrUserEntity>().lambda().eq(FrUserEntity::getOpenId, openId);
        return getOne(frUserEntityWrapper);
    }

    @Override
    public List<RecommendUserInfoResponse> getRecommendUserInfo(RecommendUserRequest request) {

        LoginUser<LoginResponse> loginUser = TokenUtils.info(LoginResponse.class);
        Integer num = Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name()));
        if (loginUser == null) {
            //未登陆
            if (request == null || request.getAge() == null || request.getGender() == null) {
                throw new JrsfException(UserExceptionEnum.SEARCH_PARAM_EMPTY_EXCEPTION);
            }
            Map<String, Object> param = new HashMap<>();
            param.put("gender", request.getGender().name());
            param.put("size", num);

            Calendar calendarStart = Calendar.getInstance();
            calendarStart.add(Calendar.YEAR, -(request.getAge() + Constants.AGE_DIFFER));
            param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));

            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.add(Calendar.YEAR, -(request.getAge() - Constants.AGE_DIFFER));
            param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));
            List<RecommendUserInfoResponse> userInfoResponse = getRecommendUserInfoByParam(param, num);
            return userInfoResponse;
        } else {
            //已登录
            FrUserEntity frUserEntity = this.getById(loginUser.getId());
            Map<String, Object> param = new HashMap<>();
            param.put("gender", frUserEntity.getGender().name());
            param.put("selfId", frUserEntity.getId());
            Integer numSize = num - Integer.valueOf(userMatchingService.matchingNum().toString());
            param.put("size", numSize);

            //默认配置信息
            Calendar calendarStart = Calendar.getInstance();
            calendarStart.setTime(frUserEntity.getBirthday());
            calendarStart.add(Calendar.YEAR, -Constants.AGE_DIFFER);
            param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStart.getTime())));
            Calendar calendarEnd = Calendar.getInstance();
            calendarEnd.setTime(frUserEntity.getBirthday());
            calendarEnd.add(Calendar.YEAR, Constants.AGE_DIFFER);
            param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEnd.getTime())));

            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                List<String> city = new ArrayList<>();
                city.add(frUserEntity.getCity());
                param.put("city", city);
            }

            //根据配置信息搜索
            RecommendConfigEntity recommendConfigEntity = this.recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
            if (recommendConfigEntity != null) {
                if (CollectionUtil.isNotEmpty(recommendConfigEntity.getCity())) {
                    param.put("city", recommendConfigEntity.getCity());
                }

                if (recommendConfigEntity.getMinAge() != null) {
                    Calendar calendarEndConfig = Calendar.getInstance();
                    calendarEndConfig.add(Calendar.YEAR, -recommendConfigEntity.getMinAge());
                    param.put("endDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.endOfYear(calendarEndConfig.getTime())));
                }

                if (recommendConfigEntity.getMaxAge() != null) {
                    Calendar calendarStartConfig = Calendar.getInstance();
                    calendarStartConfig.add(Calendar.YEAR, -recommendConfigEntity.getMaxAge());
                    param.put("startDate", DateUtils.dateToStr("yyyy-MM-dd HH:mm:ss", DateUtil.beginOfYear(calendarStartConfig.getTime())));
                }
            }

            return getRecommendUserInfoByParam(param, numSize);
        }
    }

    @Override
    @Transactional
    @Async
    public void saveRecommendConfig(FrUserEntity frUserEntity) {
        RecommendConfigEntity recommendConfigEntity = new RecommendConfigEntity();
        recommendConfigEntity.setUserId(frUserEntity.getId());
        if (StringUtils.isNotBlank(frUserEntity.getCity())) {
            List<String> city = new ArrayList<>();
            city.add(frUserEntity.getCity());
            recommendConfigEntity.setCity(city);
        }

        if (frUserEntity.getBirthday() != null) {
            Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
            recommendConfigEntity.setMinAge(age - Constants.AGE_DIFFER);
            recommendConfigEntity.setMaxAge(age + Constants.AGE_DIFFER);
        }
        recommendConfigService.save(recommendConfigEntity);
    }

    @Override
    public RecommendConfigResponse getRecommendConfig() {
        RecommendConfigResponse response = new RecommendConfigResponse();

        FrUserEntity frUserEntity = getById(TokenUtils.getOwnerId());
        if (frUserEntity != null) {

            if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                List<String> city = new ArrayList<>();
                city.add(frUserEntity.getCity());
                response.setCity(city);
            }
            if (frUserEntity.getBirthday() != null) {
                Integer age = DateUtils.getAgeByBirth(frUserEntity.getBirthday());
                response.setMaxAge(age + Constants.AGE_DIFFER);
                response.setMinAge(age - Constants.AGE_DIFFER);
            }
        }

        RecommendConfigEntity recommendConfigEntity = recommendConfigService.getRecommendConfigByUserId(TokenUtils.getOwnerId());
        if (recommendConfigEntity != null) {
            BeanUtils.copyProperties(recommendConfigEntity, response);
        }
        return response;
    }

    private List<RecommendUserInfoResponse> getRecommendUserInfoByParam(Map<String, Object> param, Integer num) {

        Integer max = 2;
        //todo 测试为2 正式时为10
        if (num > max) {
            param.put("size", max);
        }
        List<RecommendUserInfoResponse> list = this.baseMapper.getRecommendUserInfo(param);
        AtomicReference<Integer> numAt = new AtomicReference<>(num);
        if (CollectionUtil.isNotEmpty(list)) {
            list.forEach(model -> {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
                model.setConstellation(DateUtils.getConstellation(model.getBirthday()));
                model.setSurplusNum(numAt.getAndSet(numAt.get() - 1));
            });
        }
        return list;
    }
}
