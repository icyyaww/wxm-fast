package com.wxm.msfast.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.community.dao.FrUserFollowDao;
import com.wxm.msfast.community.entity.FrUserFollowEntity;
import com.wxm.msfast.community.service.FrUserFollowService;
import org.springframework.stereotype.Service;


@Service("frUserFollowService")
public class FrUserFollowServiceImpl extends ServiceImpl<FrUserFollowDao, FrUserFollowEntity> implements FrUserFollowService {

}
