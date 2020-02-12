package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

public class JoinUserException extends Exception{
    public JoinUserException() {
        super("对不起，出现异常，此用户未申请");
    }
    @ResponseBody
    @ExceptionHandler(JoinUserException.class)
    public JSONObject handleException(Exception e){
        return JSONUtils.fail(e);
    }
}
