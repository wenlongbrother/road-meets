package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public JSONObject loginWxController(@RequestBody LoginRequestDto loginRequestDto){
        try {
            JSONObject jsonObject = JSONUtils.success(wxService.loginService(loginRequestDto));
//            System.out.println(jsonObject.toJSONString());
            return jsonObject;
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return JSONUtils.fail(e);
        }
    }
    @GetMapping("/get")
    public void j(){
        System.out.println(2);
    }
}
