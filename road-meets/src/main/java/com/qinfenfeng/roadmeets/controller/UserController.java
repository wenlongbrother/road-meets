package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * User相关controller
 */
@RestController
public class UserController {
    @Autowired
    UserService wxService;
    /**
     * 登录
     * @return
     */
    @PostMapping("/loginWx")
    public JSONObject loginWxController(@RequestBody LoginRequestDto loginRequestDto) throws Exception {
        JSONObject jsonObject = JSONUtils.success(wxService.loginService(loginRequestDto));
//      System.out.println(jsonObject.toJSONString());
        return jsonObject;

    }
}
