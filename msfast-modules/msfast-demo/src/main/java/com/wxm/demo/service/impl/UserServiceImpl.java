package com.wxm.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wxm.demo.dao.UserDao;
import com.wxm.demo.service.UserService;
import com.wxm.demo.entity.UserEntity;
import org.springframework.stereotype.Service;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public void wrapper() {
        //this.baseMapper.update(new UserEntity(),new QueryWrapper<>().);
    }


}
