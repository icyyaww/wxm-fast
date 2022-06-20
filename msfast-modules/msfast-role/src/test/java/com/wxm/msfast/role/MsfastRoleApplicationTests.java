package com.wxm.msfast.role;

import com.wxm.msfast.role.entity.UserEntity;
import com.wxm.msfast.role.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class MsfastRoleApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void contextLoads() {
        List<UserEntity> entityList = userService.list();
        System.out.println(entityList);
    }

}
