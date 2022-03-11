package com.itheima.service.db.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.domain.db.Question;
import com.itheima.mapper.QuestionMapper;
import com.itheima.service.QuestionService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class QuestionServiceImpl implements QuestionService {
    @Autowired
private QuestionMapper questionMapper;
    @Override
    public Question findByUserId(Long userId) {
        LambdaQueryWrapper<Question> qw = new LambdaQueryWrapper<>();
        qw.eq(Question::getUserId,userId);
        return questionMapper.selectOne(qw);
    }
}
