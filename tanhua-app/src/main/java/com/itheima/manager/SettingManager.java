package com.itheima.manager;

import cn.hutool.core.collection.CollUtil;
import com.itheima.app.interceptor.UserHolder;
import com.itheima.domain.db.*;
import com.itheima.domain.vo.PageBeanVo;
import com.itheima.domain.vo.SettingVo;
import com.itheima.service.BlackListService;
import com.itheima.service.NotificationService;
import com.itheima.service.QuestionService;
import com.itheima.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Component
public class SettingManager {

    @DubboReference
    private QuestionService questionService;
    @DubboReference
    private NotificationService notificationService;
    @DubboReference
    private BlackListService blackListService;
    @DubboReference
    private UserInfoService userInfoService;

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

    //修改陌生人问题
    public void setQuestion(String content) {
        //获取用户
        Long id = UserHolder.get().getId();
        Question question = questionService.findByUserId(id);
        if (question != null) {
            //修改
            question.setStrangerQuestion(content);
            questionService.update(question);
        } else {
            //新增
            question = new Question();
            question.setUserId(id);
            question.setStrangerQuestion(content);
            questionService.save(question);
        }
    }

    //修改通知设置
    public void setNotification(Notification notification) {
        Long userId = UserHolder.get().getId();
        //根据用户id查询用户的通知设置
        Notification notificatio = notificationService.findByUserId(userId);

        //判断通知对象是否为空
        if (notificatio == null) {
            //保存
            notification.setUserId(userId);
            notificationService.save(notification);
        } else {
            //修改
            notification.setId(notificatio.getId());
            notificationService.update(notification);
        }
    }

    //黑名单
    public ResponseEntity findBlackListByPage(Integer pageNum, Integer pageSize) {
        Long userId = UserHolder.get().getId();
        PageBeanVo beanVo = blackListService.findByPage(pageNum, pageSize, userId);
        //用userInfoService查询黑名单用户信息,将userInfo放入一个新集合中
        // 创建一个集合 用来存放黑名单用户信息
        List<UserInfo> userInfos = new ArrayList<>();
        List<BlackList> items = (List<BlackList>) beanVo.getItems();
        if (CollUtil.isNotEmpty(items)) {
            items.forEach(item -> {
                Long blackUserId = item.getBlackUserId();
                UserInfo userInfo = userInfoService.findUserInfoById(blackUserId);
                userInfos.add(userInfo);
            });
        }
        beanVo.setItems(userInfos);
        return ResponseEntity.ok(beanVo);
    }

    public void deleteBlack(Long uid) {
        Long id = UserHolder.get().getId();
        blackListService.deleteBlack(id,uid);
    }
}
