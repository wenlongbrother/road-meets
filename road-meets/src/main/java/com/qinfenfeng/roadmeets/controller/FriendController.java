package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.EvaluateDto;
import com.qinfenfeng.roadmeets.service.FriendService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FriendController {
    @Autowired
    private FriendService friendService;
    @PostMapping("/evaluate")
    public JSONObject evaluate(@RequestBody EvaluateDto evaluateDto){
        return JSONUtils.success(friendService.evaluate(evaluateDto));
    }
    @GetMapping("/getFriend")
    public JSONObject getFriend(){
        return JSONUtils.success(friendService.getFriend());
    }
}
