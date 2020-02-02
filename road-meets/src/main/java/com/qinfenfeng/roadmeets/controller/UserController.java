package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/onlineNumber")
    public int onlineNumber(){
        return UserComponent.num();
    }
}
