package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.common.rest.response.area.AreaResponse;
import com.wxm.msfast.nostalgia.entity.AreaEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/**
 * 地区
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-26 13:40:17
 */
public interface AreaService extends IService<AreaEntity> {

    List<AreaResponse> province();

    List<AreaResponse> sonArea(String parentCode);
}

