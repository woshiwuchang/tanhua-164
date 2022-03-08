package com.itheima.app.test;

import com.itheima.autoconfig.sms.SmsTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmsTest {
    @Autowired(required = false)
    private SmsTemplate smsTemplate;

    @Test
    public void testSendSms() {
        smsTemplate.sendSms("17771415354", "1234");
        System.out.println(smsTemplate);
    }
}
