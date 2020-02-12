package com.qinfenfeng.roadmeets;

import com.qinfenfeng.roadmeets.controller.ChatController;
import com.qinfenfeng.roadmeets.controller.UserController;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class RoadMeetsApplicationTests {
    @Autowired
    UserController userController;
    @Value("${TokenPassTime}")
    private long tokenPassTime;
    @Value("${secretKey}")
    private String secretKey;
    @Value("${weixin.appID}")
    private  String appID;

    @Value("${weixin.secret}")
    private  String appsecret;
    @Autowired
    TokenComponent util ;
    @Autowired
    RedisTemplate redisTemplate;
    //登录测试
    @Test
    public void testLogin() throws Exception {
//        boolean o = userController.isExistInDB("o4_IB5YqAnHd4QRt2QdKZhEtVm7A");
//        Object o = redisTemplate.opsForValue().get("o4_IB5YqAnHd4QRt2QdKZhEtVm7A");
//        System.out.println(o);
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    ChatController chatController;
    @Test
    public void testWs()throws Exception{
//        RequestMessageDto requestMessageDto = new RequestMessageDto("1", "2", "3", "4");
//        chatController.messageHandling(requestMessageDto);
        System.out.println(appID);
    }
    @Test
    public void testprivate()throws Exception{
        UserInfoDto userInfoDto = new UserInfoDto();

    }
    @Test
    public void testFriend()throws Exception{
        Team entries = (Team) redisTemplate.opsForHash().get("teamUserId:" + 2, 48);
        System.out.println(entries);
        Object o = redisTemplate.opsForValue().get("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNTgxNDkyNDg2LCJzdWIiOiJTcXVhcmUiLCJleHAiOjE1ODIwOTcyODZ9.Y692e1sKFVZOP6K4RzEmgTnWx3lkD2daZNLisr0Jt6I");
        System.out.println(o);
    }
}


