package com.itheima.manager;

import cn.hutool.core.bean.BeanUtil;
import com.itheima.app.interceptor.UserHolder;
import com.itheima.domain.db.User;
import com.itheima.domain.db.UserInfo;
import com.itheima.domain.vo.UserInfoVo;
import com.itheima.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserInfoManager {
    @DubboReference
    private UserInfoService userInfoService;
    public ResponseEntity findUserInfoById(Long userID) {
        UserInfo info = userInfoService.findUserInfoById(userID);
        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtil.copyProperties(info,userInfoVo);
        return ResponseEntity.ok(userInfoVo);
    }

    public ResponseEntity updateUserInfo(UserInfo userInfo) {
        User user = UserHolder.get();
        userInfoService.updateHeadPhoto(userInfo);
        return ResponseEntity.ok(null);
    }
}
