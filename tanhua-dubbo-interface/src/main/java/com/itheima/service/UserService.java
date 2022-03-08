package com.itheima.service;

import com.itheima.domain.db.User;

public interface UserService {
    Long save(User user);
    User findByPhone(String mobile);
}
