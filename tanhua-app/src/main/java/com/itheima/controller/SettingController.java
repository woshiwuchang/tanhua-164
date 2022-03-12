package com.itheima.controller;

import com.itheima.domain.db.Notification;
import com.itheima.domain.vo.PageBeanVo;
import com.itheima.manager.SettingManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class SettingController {
    @Autowired
    private SettingManager settingManager;

    @GetMapping("/settings")
    public ResponseEntity findSetting() {
        return settingManager.findSetting();
    }

    @PostMapping("/questions")
    public void setQuestion(@RequestBody Map<String, String> map) {
        String content = map.get("content");
        settingManager.setQuestion(content);
    }

    @PostMapping("/notifications/setting")
    public void setNotification(@RequestBody Notification notification) {
        settingManager.setNotification(notification);
    }

    //黑名单
    @GetMapping("/blacklist")
    public ResponseEntity findBlackListByPage(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pagesize", defaultValue = "7") Integer pageSize) {
        return settingManager.findBlackListByPage(pageNum, pageSize);
    }

    @DeleteMapping("/blacklist/{uid}")
    public void deleteBlackList(@PathVariable Long uid) {
        settingManager.deleteBlack(uid);
    }
}
