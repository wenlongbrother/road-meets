package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoUserException extends Exception{
    public NoUserException() {
        super("对不起，请登录");
    }
    @ResponseBody
    @ExceptionHandler(ReleaseTeamException.class)
    public JSONObject handleException(Exception e){
        return JSONUtils.fail(e);
    }
}
