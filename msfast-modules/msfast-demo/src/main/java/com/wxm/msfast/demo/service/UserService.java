package com.wxm.msfast.demo.service;

import com.wxm.msfast.base.server.service.JrsfService;
import com.wxm.msfast.demo.common.rest.request.UserAddRequest;
import com.wxm.msfast.demo.entity.User;
import org.springframework.stereotype.Service;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2022-06-25 18:55
 **/
@Service
public class UserService extends JrsfService<UserAddRequest, User> {
}
