package com.itheima.service.db.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.domain.db.BlackList;
import com.itheima.domain.vo.PageBeanVo;
import com.itheima.mapper.BlackListMapper;
import com.itheima.service.BlackListService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

@DubboService
public class BlackListServiceImpl implements BlackListService {
    @Autowired
    private BlackListMapper blackListMapper;
    @Override
    public PageBeanVo findByPage(Integer pageNum, Integer pageSize, Long userId) {
        Page<BlackList> page = new Page<>(pageNum,pageSize);
            //查询当前用户的黑名单
        LambdaQueryWrapper<BlackList> qw = new LambdaQueryWrapper<>();
        qw.eq(BlackList::getUserId,userId);
        blackListMapper.selectPage(page,qw);
        PageBeanVo pageBeanVo = new PageBeanVo();
        pageBeanVo.setPage(pageNum);
        pageBeanVo.setPagesize(pageSize);
        pageBeanVo.setItems(page.getRecords());
        pageBeanVo.setCounts(page.getTotal());
        return pageBeanVo;
    }
//取消黑名单
    @Override
    public void deleteBlack(Long id, Long uid) {
        LambdaQueryWrapper<BlackList> qw = new LambdaQueryWrapper<>();
        qw.eq(BlackList::getUserId,id).eq(BlackList::getBlackUserId,uid);
        blackListMapper.delete(qw);
    }
}
