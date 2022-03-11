package com.itheima.controller;

import com.itheima.app.interceptor.UserHolder;
import com.itheima.domain.db.User;
import com.itheima.domain.db.UserInfo;
import com.itheima.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserManager userManager;

    //用户保存
    @PostMapping("/save")
    public Long save(@RequestBody User user) {
        return userManager.save(user);
    }

    //通过手机号查用户
    @GetMapping("/findByPhone")
    public User findByPhone(String phone) {
        return userManager.findByPhone(phone);
    }

    //获取登录验证码
    @PostMapping("/login")
    public void sendCode(@RequestBody Map<String, String> map) {
        String phone = map.get("phone");
        userManager.sendCode(phone);
    }

    //登陆注册
    @PostMapping("/loginVerification")
    public ResponseEntity regAndLogin(@RequestBody Map<String, String> map) {
        ResponseEntity regAndLogin = userManager.regAndLogin(map);
        return regAndLogin;
    }

    @PostMapping("/loginReginfo")
    //首次登录---完善资料
    public void saveUserInfo(@RequestBody UserInfo userInfo, @RequestHeader("Authorization") String token) {
        userManager.saveUserInfo(userInfo, token);
    }

    @PostMapping("/loginReginfo/head")
    public void saveUserInfoHeard(MultipartFile headPhoto,@RequestHeader("Authorization") String token) throws IOException {
        userManager.saveUserInfoHeard(headPhoto,token);
    }
}
