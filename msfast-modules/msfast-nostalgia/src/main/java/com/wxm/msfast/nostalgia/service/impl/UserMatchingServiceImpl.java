package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.utils.DateUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.UserMatchingDao;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.wxm.msfast.nostalgia.service.UserMatchingService;

import java.util.Calendar;
import java.util.Date;


@Service("userMatchingService")
public class UserMatchingServiceImpl extends ServiceImpl<UserMatchingDao, UserMatchingEntity> implements UserMatchingService {


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
}
