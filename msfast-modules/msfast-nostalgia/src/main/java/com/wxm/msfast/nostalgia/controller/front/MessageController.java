package com.wxm.msfast.nostalgia.controller.front;

import com.wxm.msfast.base.auth.utils.TokenUtils;
import com.wxm.msfast.base.common.constant.ParamTypeConstants;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.domain.R;
import com.wxm.msfast.base.websocket.common.rest.response.MessageInfoResponse;
import com.wxm.msfast.base.websocket.service.MsFastMessageService;
import com.wxm.msfast.base.websocket.utils.ChannelUtil;
import com.wxm.msfast.nostalgia.common.rest.response.front.matching.LikeMePageResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-13 17:20
 **/

@RestController
@RequestMapping("message")
@Api(tags = "消息通讯")
@Slf4j
public class MessageController {

    @Autowired
    MsFastMessageService msFastMessageService;


    @Autowired
    ChannelUtil channelUtil;

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "start", value = "起始下标", defaultValue = "0"),
            @ApiImplicitParam(paramType = ParamTypeConstants.requestParam, name = "end", value = "结束下标", defaultValue = "10")
    })
    @ApiOperation("对话框详情")
    @ApiOperationSort(value = 1)
    @GetMapping("/info/{userId}")
    public R<Set<MessageInfoResponse>> messageInfoList(
            @PathVariable Integer userId,
            @RequestParam(value = "start", required = false, defaultValue = "0") Long start,
            @RequestParam(value = "end", required = false, defaultValue = "10") Long end) {
        return R.ok(msFastMessageService.getMessageInfoRange(channelUtil.getMessageInfoKey(TokenUtils.getOwnerId(), userId), start, end));
    }

}
