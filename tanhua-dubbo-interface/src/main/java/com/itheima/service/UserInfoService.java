package com.itheima.service;

import com.itheima.domain.db.UserInfo;
import com.itheima.domain.vo.UserInfoVo;

public interface UserInfoService {
    void save(UserInfo userInfo);

    void updateHeadPhoto(UserInfo userInfo);

    UserInfo findUserInfoById(Long userID);

}
