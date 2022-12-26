package com.wxm.msfast.nostalgia.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.nostalgia.common.rest.response.area.AreaResponse;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.nostalgia.dao.AreaDao;
import com.wxm.msfast.nostalgia.entity.AreaEntity;
import com.wxm.msfast.nostalgia.service.AreaService;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("areaService")
public class AreaServiceImpl extends ServiceImpl<AreaDao, AreaEntity> implements AreaService {


    @Override
    public List<AreaResponse> province() {
        Map<String, Object> param = new HashMap<>();
        param.put("province", true);
        return this.baseMapper.province(param);
    }

    @Override
    public List<AreaResponse> sonArea(String parentCode) {
        Map<String, Object> param = new HashMap<>();
        param.put("parentCode", parentCode);
        return this.baseMapper.province(param);
    }
}
