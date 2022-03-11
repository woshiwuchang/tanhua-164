package com.itheima.manager;

import com.itheima.app.interceptor.UserHolder;
import com.itheima.domain.db.Notification;
import com.itheima.domain.db.Question;
import com.itheima.domain.db.User;
import com.itheima.domain.vo.SettingVo;
import com.itheima.service.NotificationService;
import com.itheima.service.QuestionService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SettingManager {

@DubboReference
private QuestionService questionService;
@DubboReference
private NotificationService notificationService;

    public ResponseEntity findSetting() {
        User user = UserHolder.get();
        //远程调用service查询问题
        Question question = questionService.findByUserId(user.getId());
        Notification notification = notificationService.findByUserId(user.getId());
        //封装vo对象且返回
        SettingVo vo = new SettingVo();
        vo.setId(user.getId());
        vo.setPhone(user.getMobile());
        //设置陌生人问题
        if (question != null) {
            vo.setStrangerQuestion(question.getStrangerQuestion());
        }
        //设置 通知设置
        if (notification != null) {
            vo.setPinglunNotification(notification.getPinglunNotification());
            vo.setLikeNotification(notification.getLikeNotification());
            vo.setGonggaoNotification(notification.getGonggaoNotification());
        }
        return ResponseEntity.ok(vo);
    }
}
