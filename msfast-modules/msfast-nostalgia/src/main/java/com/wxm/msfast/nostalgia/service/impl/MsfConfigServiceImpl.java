package com.wxm.msfast.nostalgia.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.MsfConfigDao;
import com.wxm.msfast.nostalgia.entity.MsfConfigEntity;
import com.wxm.msfast.nostalgia.service.MsfConfigService;


@Service("msfConfigService")
public class MsfConfigServiceImpl extends ServiceImpl<MsfConfigDao, MsfConfigEntity> implements MsfConfigService {

}
