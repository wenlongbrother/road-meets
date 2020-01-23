package com.qinfenfeng.roadmeets;

import com.qinfenfeng.roadmeets.controller.UserController;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoadMeetsApplicationTests {
    @Autowired
    UserController userController;
    @Value("${TokenPassTime}")
    private long tokenPassTime;
    @Value("${secretKey}")
    private String secretKey;
    @Autowired
    TokenComponent util ;

    //登录测试
    @Test
    public void testLogin() throws Exception {
        LoginRequestDto loginRequestDto = new LoginRequestDto("1","1", "1");
        userController.isExistInDB("o4_IB5Tzm8q5tNgz0yWIvejfa3nY");
    }

}
