package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * User相关controller
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 登录
     * @return
     */
    @PostMapping("/loginWx")
    public JSONObject loginWxController(@RequestBody LoginRequestDto loginRequestDto){
        try {
            JSONObject jsonObject = JSONUtils.success(userService.loginService(loginRequestDto));
//            System.out.println(jsonObject.toJSONString());
            return jsonObject;
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return JSONUtils.fail(e);
        }
    }
    @PostMapping("/userLocation")
    public JSONObject userLocation(HttpServletRequest request, @RequestBody LocationDto locationDto)throws Exception{
        String token = request.getHeader("token");
        boolean result = userService.userLocation(token, locationDto.getLongitude(), locationDto.getLatitude());
        return JSONUtils.success(result);
    }

    /**
     * 获得用户的信息
     * @param userId
     * @return
     * @throws NoUserException
     */
    @GetMapping("/getUserInfoById")
    public JSONObject getUserInfoById(Long userId) throws NoUserException {
        return JSONUtils.success(userService.getUserInfoByUserId(userId));
    }
    @GetMapping("/getUserInfo")
    public JSONObject getUserInfoByToken() throws NoUserException {
        return JSONUtils.success(userService.getUserInfoByToken());
    }

    @GetMapping("/onlineNumber")
    public JSONObject onlineNumber(LocationDto locationDto){
        return JSONUtils.success(userService.onlineNumber(locationDto));
    }




}
