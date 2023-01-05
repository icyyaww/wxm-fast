package com.wxm.msfast.nostalgia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.nostalgia.common.rest.request.fruser.ChoiceRequest;
import com.wxm.msfast.nostalgia.common.rest.response.matching.LikeMePageResponse;
import com.wxm.msfast.nostalgia.common.rest.response.matching.SuccessPageResponse;
import com.wxm.msfast.nostalgia.entity.UserMatchingEntity;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * 用户匹配
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
public interface UserMatchingService extends IService<UserMatchingEntity> {

    /**
     * @Description: 查询已经匹配的数量
     * @Param:
     * @return:
     * @Author: Mr.Wang
     * @Date: 2022/12/12 下午2:31
     */
    Long matchingNum();

    void match(ChoiceRequest request);

    PageResult<LikeMePageResponse> likeMePage(Integer pageIndex, Integer pageSize);

    PageResult<SuccessPageResponse> successPage(Integer pageIndex, Integer pageSize);

}

