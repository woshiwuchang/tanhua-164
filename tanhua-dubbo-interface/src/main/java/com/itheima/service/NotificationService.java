package com.itheima.service;

import com.itheima.domain.db.Notification;

public interface NotificationService {
    Notification findByUserId(Long userId);

    void save(Notification notification);

    void update(Notification notification);

}
