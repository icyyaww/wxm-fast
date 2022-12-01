package com.wxm.msfast.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.RecommendConfigDao;
import com.wxm.msfast.nostalgia.entity.RecommendConfigEntity;
import com.wxm.msfast.nostalgia.service.RecommendConfigService;


@Service("recommendConfigService")
public class RecommendConfigServiceImpl extends ServiceImpl<RecommendConfigDao, RecommendConfigEntity> implements RecommendConfigService {

}
