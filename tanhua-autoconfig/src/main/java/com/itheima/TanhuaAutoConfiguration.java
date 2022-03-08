package com.itheima;

import com.itheima.autoconfig.sms.SmsProperties;
import com.itheima.autoconfig.sms.SmsTemplate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({
        SmsProperties.class //加载短信配置
})
public class TanhuaAutoConfiguration {
    @Bean
    public SmsTemplate smsTemplate(SmsProperties smsProperties) {
        return new SmsTemplate(smsProperties);
    }
}
