package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoTeamException extends NullPointerException{
    public NoTeamException() {
        super("对不起，无此行程");
    }
    @ResponseBody
    @ExceptionHandler(NoTeamException.class)
    public JSONObject handleException(Exception e){
        JSONObject jsonObject = JSONUtils.fail(e);
        return jsonObject;
    }
}
