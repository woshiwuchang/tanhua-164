package com.itheima.test;

import cn.hutool.core.io.FileUtil;
import com.itheima.autoconfig.face.AipFaceTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AipFaceTest {
    @Autowired
    private AipFaceTemplate template;

    @Test
    public void testAip() throws IOException {
        String filename = "E:\\c7dc0a4a1ed6d9e68aa3a5280dd20b8c.jpeg";
        File file = new File(filename);
        byte[] bytes = FileUtil.readBytes(file);
        System.out.println(template.detect(bytes));
    }
}
