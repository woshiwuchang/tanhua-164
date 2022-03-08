package com.itheima.manager;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.itheima.autoconfig.sms.SmsProperties;
import com.itheima.autoconfig.sms.SmsTemplate;
import com.itheima.domain.db.User;
import com.itheima.domain.vo.ErrorResult;
import com.itheima.service.UserService;
import com.itheima.util.ConstantUtil;
import com.itheima.util.JwtUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserManager {
    @DubboReference
    private UserService userService;
    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    //保存用户
    public Long save(User user) {
        Long id = userService.save(user);
        return id;
    }

    //根据手机号查用户
    public User findByPhone(String mobile) {
        User user = userService.findByPhone(mobile);
        return user;
    }

    /**
     * 发送验证码
     *
     * @param phone 手机号码
     */
    public void sendCode(String phone) {
        //生成验证码
        String code = RandomUtil.randomNumbers(6);
        //发送短信
        code="123456";
        //smsTemplate.sendSms(phone, code);
        stringRedisTemplate.opsForValue().set(ConstantUtil.SMS_CODE + phone, code, Duration.ofMinutes(5));
    }

    /**
     * 登录注册
     *
     * @param map phone 手机号
     *            verificationCode 验证码
     * @return
     */
    public ResponseEntity regAndLogin(Map<String, String> map) {
        String phone = map.get("phone");
        String verificationCode = map.get("verificationCode");
        //redis中的验证码
        String redisCode = stringRedisTemplate.opsForValue().get(ConstantUtil.SMS_CODE + phone);

        if (!StrUtil.equals(verificationCode, redisCode)) {
            return ResponseEntity.status(500).body(ErrorResult.codeError());
        }
        //通过手机号判断用户是否存在
        User user = userService.findByPhone(phone);
        Boolean isNew;
        //判断新老用户
        if (user != null) {
            isNew = false;
        } else {
            isNew = true;
            user = new User();
            user.setMobile(phone);
            user.setPassword(ConstantUtil.INIT_PASSWORD);
            Long id = userService.save(user);
            user.setId(id);
        }
        //token除去密码
        user.setPassword(null);
        Map<String, Object> userMap = BeanUtil.beanToMap(user);
        String token = JwtUtil.createToken(userMap);
        //为了避免每次都解析token,将token作为key,用户的json数据做为value,放入redis中
        stringRedisTemplate.opsForValue().
                set(ConstantUtil.USER_TOKEN + token, JSON.toJSONString(user), Duration.ofDays(30));
        //从redis中把验证码删除
        stringRedisTemplate.delete(ConstantUtil.SMS_CODE + phone);
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("isNew",isNew);
        return ResponseEntity.ok(tokenMap);
    }
}
