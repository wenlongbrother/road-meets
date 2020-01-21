package com.qinfenfeng.roadmeets;

import com.qinfenfeng.roadmeets.controller.WxController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoadMeetsApplicationTests {
    @Autowired
    WxController wxController;

    //登录测试
    @Test
    public void testLogin() {
        wxController.loginController("1");
    }

}
