package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.PhotoEditRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendConfigRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.PersonalCenterResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.PersonalInfoResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendConfigResponse;
import com.wxm.msfast.nostalgia.common.rest.response.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.entity.FrUserEntity;
import org.springframework.scheduling.annotation.Async;

import java.util.List;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
public interface FrUserService extends IService<FrUserEntity> {

    Long countByOpenId(String openId);

    FrUserEntity getFrUserByOpenId(String openId);

    List<RecommendUserInfoResponse> getRecommendUserInfo(RecommendUserRequest request);

    void saveRecommendConfig(FrUserEntity frUserEntity);

    RecommendConfigResponse getRecommendConfig();

    void updateConfigInfo(RecommendConfigRequest request);

    @Async
    void updateLatelyTime(Integer userId);

    PersonalCenterResponse getPersonalCenter();

    PersonalInfoResponse personalInfo();

    void photoEdit(PhotoEditRequest request);
}

