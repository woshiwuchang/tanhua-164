package com.itheima.service;

import com.itheima.domain.db.Question;

public interface QuestionService {
    Question findByUserId(Long userId);

    void update(Question question);

    void save(Question question);
}
