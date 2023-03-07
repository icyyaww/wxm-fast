package com.wxm.msfast.nostalgia.controller.front;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wxm.msfast.base.common.annotation.AuthIgnore;
import com.wxm.msfast.base.common.utils.TokenUtils;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.RecommendUserRequest;
import com.wxm.msfast.nostalgia.common.rest.response.front.fruser.RecommendUserInfoResponse;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import com.wxm.msfast.nostalgia.service.FrUserService;
import com.wxm.msfast.nostalgia.service.TestService;
import com.wxm.msfast.nostalgia.service.UserMatchingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiOperationSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-10-17 10:12
 **/

@RestController
@Api(tags = "测试接口")
@RequestMapping("test")
public class TestController {

    @Autowired
    UserMatchingService userMatchingService;

    @Autowired
    FrUserService frUserService;

    @Autowired
    TestService testService;

    @ApiOperation("重置匹配结果")
    @ApiOperationSort(value = 1)
    @PostMapping("/reload/matching")
    @AuthIgnore
    public R<Void> reloadMatching() {

        Wrapper<UserMatchingEntity> wrapper = new QueryWrapper<UserMatchingEntity>().lambda().eq(UserMatchingEntity::getUserId, TokenUtils.getOwnerId());
        userMatchingService.remove(wrapper);
        return R.ok();
    }

    @ApiOperation("删除前台用户")
    @ApiOperationSort(value = 1)
    @GetMapping("/delete/fruser/{id}")
    @AuthIgnore
    public R<Void> deleteFruser(@PathVariable Integer id) {
        testService.deleteFruser(id);
        return R.ok();
    }

    @ApiOperation("测试")
    @ApiOperationSort(value = 1)
    @GetMapping("/matching")
    public R<Void> matching() {

        RecommendUserInfoResponse userInfoResponse = frUserService.getRecommendUserInfo(new RecommendUserRequest());
        ChoiceRequest choiceRequest = new ChoiceRequest();
        choiceRequest.setOtherUser(userInfoResponse.getId());
        choiceRequest.setResult(true);
        userMatchingService.match(choiceRequest);

        return R.ok();
    }

}
