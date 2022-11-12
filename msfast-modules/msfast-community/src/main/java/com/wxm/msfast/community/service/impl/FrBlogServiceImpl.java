package com.wxm.msfast.community.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.community.dao.FrBlogDao;
import com.wxm.msfast.community.entity.FrBlogEntity;
import com.wxm.msfast.community.service.FrBlogService;


@Service("frBlogService")
public class FrBlogServiceImpl extends ServiceImpl<FrBlogDao, FrBlogEntity> implements FrBlogService {

}
