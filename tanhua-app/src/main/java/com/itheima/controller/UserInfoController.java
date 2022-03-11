package com.itheima.controller;

import com.itheima.app.interceptor.UserHolder;
import com.itheima.domain.db.User;
import com.itheima.domain.db.UserInfo;
import com.itheima.manager.UserInfoManager;
import com.itheima.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UserInfoController {
    @Autowired(required = false)
    private UserInfoManager userInfoManager;
    @Autowired
    private UserManager userManager;

    @GetMapping
    public ResponseEntity findUserInfoById(@RequestHeader("Authorization") String token,
                                           @RequestParam(required = false) Long userID) {
        if (userID != null) {
            return userInfoManager.findUserInfoById(userID);
        } else {
            User user = UserHolder.get();
            if (user == null) {
                return ResponseEntity.status(401).body(null);
            }
            return userInfoManager.findUserInfoById(user.getId());
        }
    }
@PutMapping
    //修改基本信息
    public ResponseEntity updateUserInfo(@RequestBody UserInfo userInfo, @RequestHeader("Authorization") String token) {
        return userInfoManager.updateUserInfo(userInfo);
    }

    // 更新用户头像
    @PostMapping("/header")
    public ResponseEntity saveUserInfoHead(@RequestHeader("Authorization") String token, MultipartFile headPhoto) throws IOException {
        return userManager.saveUserInfoHeard(headPhoto, token);
    }
}
