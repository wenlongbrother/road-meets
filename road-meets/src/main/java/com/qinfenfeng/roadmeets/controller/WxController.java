package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.service.WxService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信相关controller
 */
@RestController
public class WxController {
    @Autowired
    WxService wxService;
    /**
     * 登录
     * @return
     */
    @GetMapping("/login")
    public JSONObject loginController(String jsCode){
        try {
            JSONObject jsonObject = JSONUtils.success(wxService.loginService(jsCode));
//            System.out.println(jsonObject.toJSONString());
            return jsonObject;
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            return JSONUtils.fail(e);
        }
    }
}
