package com.itheima.service.db.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itheima.domain.db.User;
import com.itheima.mapper.UserMapper;
import com.itheima.service.UserService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
@DubboService
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public Long save(User user) {
        //插入user
        userMapper.insert(user);
        //返回id
        return user.getId();
    }

    @Override
    public User findByPhone(String mobile) {
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(User::getMobile,mobile);
        return userMapper.selectOne(qw);
    }
}
