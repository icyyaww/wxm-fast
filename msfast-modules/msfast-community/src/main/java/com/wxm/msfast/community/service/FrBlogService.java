package com.wxm.msfast.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wxm.msfast.community.common.rest.response.user.PersonalCenterBlogResponse;
import com.wxm.msfast.community.entity.FrBlogEntity;

import java.util.List;
import java.util.Map;

/**
 * 用户日志
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-11-12 17:05:01
 */
public interface FrBlogService extends IService<FrBlogEntity> {

    /*
     * @Author 用户发布的动态数量
     * @Description
     * @Date 17:12 2022/11/12
     * @Param
     * @return
     **/
    Long userBlogCount();

    /*
     * @Author 查询个人中心的日志预览
     * @Description
     * @Date 20:13 2022/11/12
     * @Param
     * @return
     **/
    List<PersonalCenterBlogResponse> getPersonalBlogImage();
}

