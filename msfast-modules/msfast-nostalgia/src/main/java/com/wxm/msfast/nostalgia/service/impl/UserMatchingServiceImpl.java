package com.wxm.msfast.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.UserMatchingDao;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.wxm.msfast.nostalgia.service.UserMatchingService;


@Service("userMatchingService")
public class UserMatchingServiceImpl extends ServiceImpl<UserMatchingDao, UserMatchingEntity> implements UserMatchingService {

}
