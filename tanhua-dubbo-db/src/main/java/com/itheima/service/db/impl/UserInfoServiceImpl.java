package com.itheima.service.db.impl;

import com.itheima.domain.db.UserInfo;
import com.itheima.domain.vo.UserInfoVo;
import com.itheima.mapper.UserInfoMapper;
import com.itheima.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoMapper userInfoMapper;

    //保存用户信息
    @Override
    public void save(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    //更新用户头像
    @Override
    public void updateHeadPhoto(UserInfo userInfo) {
        userInfoMapper.updateById(userInfo);
    }

    @Override
    public UserInfo findUserInfoById(Long userID) {
        UserInfo userInfo = userInfoMapper.selectById(userID);
        return userInfo;
    }
}
