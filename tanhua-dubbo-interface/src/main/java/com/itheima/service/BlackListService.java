package com.itheima.service;

import com.itheima.domain.vo.PageBeanVo;

public interface BlackListService {

    PageBeanVo findByPage(Integer pageNum, Integer pageSize, Long userId);

    void deleteBlack(Long id, Long uid);

}
