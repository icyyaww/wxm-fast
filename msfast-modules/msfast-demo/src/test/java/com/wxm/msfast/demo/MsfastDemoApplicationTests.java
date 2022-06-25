package com.wxm.msfast.demo;

import com.wxm.msfast.demo.entity.User;
import com.wxm.msfast.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class MsfastDemoApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    @Transactional
    void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        User user = new User();
        user.setAge(10);
        user.setEmail("1236@qq.com");
        user.setName("张三");
        user.setId(6l);
        userMapper.insert(user);
        userList.forEach(System.out::println);
    }

}
