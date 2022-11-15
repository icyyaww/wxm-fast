package com.wxm.msfast.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.msfast.base.common.utils.PageResult;
import com.wxm.msfast.base.common.web.page.Query;
import com.wxm.msfast.demo.dao.UserDao;
import com.wxm.msfast.demo.entity.UserEntity;
import com.wxm.msfast.demo.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public void wrapper() {
        //this.baseMapper.update(new UserEntity(),new QueryWrapper<>().);
    }


}
