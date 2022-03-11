package com.itheima.controller;

import com.itheima.manager.SettingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class SettingController {
    @Autowired
    private SettingManager settingManager;

    @GetMapping("/settings")
    public ResponseEntity findSetting() {
return settingManager.findSetting();
    }
}
