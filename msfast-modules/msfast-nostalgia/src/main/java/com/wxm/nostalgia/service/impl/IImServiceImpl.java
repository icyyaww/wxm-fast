package com.wxm.nostalgia.service.impl;

import com.wxm.base.common.service.BaseCommonService;
import com.wxm.base.websocket.common.constant.WebSocketConstants;
import com.wxm.base.websocket.common.rest.response.ImUserInfoResponse;
import com.wxm.base.websocket.service.IImService;
import com.wxm.nostalgia.entity.FrUserEntity;
import com.wxm.nostalgia.service.FrUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-02-10 13:58
 **/

@Service
public class IImServiceImpl implements IImService {

    @Autowired
    BaseCommonService baseCommonService;

    @Autowired
    FrUserService frUserService;

    @Override
    public ImUserInfoResponse getImUser(Integer userId) {

        ImUserInfoResponse imUserInfoResponse = new ImUserInfoResponse();
        FrUserEntity frUserEntity = frUserService.getById(userId);
        if (frUserEntity != null) {
            Map<String, Object> map = new HashMap<>();
            map.put(WebSocketConstants.HEAD_PORTRAIT, frUserEntity.getHeadPortrait());
            map.put(WebSocketConstants.NICK_NAME, frUserEntity.getNickName());
            baseCommonService.updateUser(frUserEntity.getId(), map);

            imUserInfoResponse.setHeadPortrait(frUserEntity.getHeadPortrait());
            imUserInfoResponse.setNickName(frUserEntity.getNickName());
        }
        return imUserInfoResponse;
    }
}
