package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class FriendException extends Exception{
    public FriendException() {
        super("和对方不是好友");
    }
    @ResponseBody
    @ExceptionHandler(FriendException.class)
    public JSONObject handleException(Exception e){
        JSONObject jsonObject = JSONUtils.fail(e);
        return jsonObject;
    }
}
