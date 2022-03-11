package com.itheima.app.interceptor;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itheima.domain.db.User;
import com.itheima.util.ConstantUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求传过来的token
        String token = request.getHeader("Authorization");
        if (StrUtil.isNotBlank(token)) {
            //从redis中取出user对象
            String jsonUser = stringRedisTemplate.opsForValue().get(ConstantUtil.USER_TOKEN + token);
            if (StrUtil.isNotBlank(jsonUser)) {
                //jsonUser不为空转成对象
                User user = JSON.parseObject(jsonUser, User.class);
                if (user != null) {
                    //绑定到当前的线程中
                    UserHolder.set(user);
                    //token在redis中续期\
                    stringRedisTemplate.opsForValue().set(ConstantUtil.USER_TOKEN + token, jsonUser, Duration.ofDays(30));
                    return true;
                }
            }
        }
        //上面的判断只要有一条不满足就不放行，401会跳首页
        response.setStatus(401);
        return false;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.remove();
    }
}
