package com.wxm.msfast.nostalgia.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wxm.msfast.base.auth.service.MsfConfigService;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.enums.GenderEnum;
import com.wxm.msfast.base.common.exception.JrsfException;
import com.wxm.msfast.base.common.utils.DateUtils;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.utils.SpringBeanUtils;
import com.wxm.msfast.base.common.utils.SpringUtils;
import com.wxm.msfast.nostalgia.common.constant.Constants;
import com.wxm.msfast.nostalgia.common.enums.SysConfigCodeEnum;
import com.wxm.msfast.nostalgia.common.exception.UserExceptionEnum;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxm.msfast.nostalgia.common.rest.response.matching.LikeMePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.LikePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.SuccessPageResponse;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.UserMatchingDao;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.wxm.msfast.nostalgia.service.UserMatchingService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;


@Service("userMatchingService")
public class UserMatchingServiceImpl extends ServiceImpl<UserMatchingDao, UserMatchingEntity> implements UserMatchingService {

    @Autowired
    MsfConfigService msfConfigService;

    @Autowired
    RedissonClient redissonClient;


    @Override
    public Long matchingNum() {

        Calendar calendar = Calendar.getInstance();
        Integer hour = calendar.get(Calendar.HOUR_OF_DAY);
        QueryWrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>();
        wrapper.lambda().eq(UserMatchingEntity::getUserId, TokenUtils.getOwnerId());
        if (hour < 12) {
            Calendar calendarLast = Calendar.getInstance();
            Date yesterday = new Date(DateUtils.getStartTimeOfDay(calendar.getTime()).getTime() - 1);
            calendarLast.setTime(yesterday);
            String yesterStr = calendarLast.get(Calendar.YEAR) + "-" + (calendarLast.get(Calendar.MONTH) + 1) + "-" + calendarLast.get(Calendar.DATE) + " 12:00:00";
            //前一天12点
            Date yesterdayNoon = DateUtils.parseDate(yesterStr);
            wrapper.apply("create_time>={0} and create_time<{1}", yesterdayNoon, calendar.getTime());
        } else {
            String strTodayNoon = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " 12:00:00";
            Date todayNoon = DateUtils.parseDate(strTodayNoon);
            wrapper.apply("create_time>={0} and create_time<{1}", todayNoon, calendar.getTime());
        }
        return this.baseMapper.selectCount(wrapper);
    }

    @Override
    @Transactional
    public void match(ChoiceRequest request) {

        RLock lock = redissonClient.getLock(Constants.MATCHING + TokenUtils.getOwnerId());
        try {
            lock.lock();

            Integer num = Integer.valueOf(msfConfigService.getValueByCode(SysConfigCodeEnum.recommendTotal.name()));
            Integer count = Integer.parseInt(this.matchingNum().toString());
            if (num.compareTo(count) <= 0) {
                throw new JrsfException(UserExceptionEnum.MATCHING_BEYOND_LIMIT_EXCEPTION);
            }

            Integer userId = TokenUtils.getOwnerId();
            Wrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>().lambda()
                    .eq(UserMatchingEntity::getUserId, userId)
                    .eq(UserMatchingEntity::getOtherUser, request.getOtherUser());
            Long matchingCount = this.baseMapper.selectCount(wrapper);
            if (matchingCount == 0) {
                UserMatchingEntity userMatchingEntity = new UserMatchingEntity();
                BeanUtils.copyProperties(request, userMatchingEntity);
                userMatchingEntity.setUserId(userId);

                if (userMatchingEntity.getResult()) {
                    int random = RandomUtil.randomInt(0, 3);
                    FrUserService frUserService = SpringUtils.getBean(FrUserService.class);
                    FrUserEntity frUserEntity = frUserService.getById(userId);
                    if (frUserEntity != null) {
                        String gender = GenderEnum.MALE.equals(frUserEntity.getGender()) ? "小哥哥" : "小姐姐";
                        switch (random) {
                            case 0:
                                if (StringUtils.isNotBlank(frUserEntity.getCity())) {
                                    userMatchingEntity.setDescInfo("一个" + frUserEntity.getCity() + "的" + gender);
                                }
                                break;
                            case 1:
                                userMatchingEntity.setDescInfo("一个" + DateUtils.getConstellation(frUserEntity.getBirthday()) + "的" + gender);
                                break;
                            default:
                                if (frUserEntity.getBirthday() != null) {
                                    userMatchingEntity.setDescInfo("一个" + DateUtils.getAgeByBirth(frUserEntity.getBirthday()) + "岁的" + gender);
                                }
                        }
                    }

                }

                this.baseMapper.insert(userMatchingEntity);
            }
        } finally {
            lock.unlock();
        }

    }

    @Override
    public PageResult<LikeMePageResponse> likeMePage(Integer pageIndex, Integer pageSize) {
        Page<LikeMePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getLikeMePage(TokenUtils.getOwnerId());
        PageResult<LikeMePageResponse> result = new PageResult<>(page);
        return result;
    }

    @Override
    public PageResult<SuccessPageResponse> successPage(Integer pageIndex, Integer pageSize) {
        Page<SuccessPageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getSuccessPage(TokenUtils.getOwnerId());
        PageResult<SuccessPageResponse> result = new PageResult<>(page);
        result.getRows().forEach(model -> {
            if (model.getBirthday() != null) {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
            }
        });
        return result;
    }

    @Override
    public PageResult<LikePageResponse> likePage(Integer pageIndex, Integer pageSize) {
        Page<LikePageResponse> page = PageHelper.startPage(pageIndex, pageSize);
        this.getBaseMapper().getLikePage(TokenUtils.getOwnerId());
        PageResult<LikePageResponse> result = new PageResult<>(page);
        result.getRows().forEach(model -> {
            if (model.getBirthday() != null) {
                model.setAge(DateUtils.getAgeByBirth(model.getBirthday()));
            }
        });
        return result;
    }
}
