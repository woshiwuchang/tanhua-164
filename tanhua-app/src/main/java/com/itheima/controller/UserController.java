package com.itheima.controller;

import com.itheima.domain.db.User;
import com.itheima.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserManager userManager;

    @PostMapping("/save")
    public Long save(@RequestBody User user) {
        return userManager.save(user);
    }

    @GetMapping("/findByPhone")
    public User findByPhone(String phone) {
        return userManager.findByPhone(phone);
    }
    @PostMapping("/login")
    public void sendCode(@RequestBody Map<String,String> map){
        String phone = map.get("phone");
        userManager.sendCode(phone);
    }
@PostMapping("/loginVerification")
    public ResponseEntity regAndLogin(@RequestBody Map<String,String> map){
    ResponseEntity regAndLogin = userManager.regAndLogin(map);
    return regAndLogin;
}
}
