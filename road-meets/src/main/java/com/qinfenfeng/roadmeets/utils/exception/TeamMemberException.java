package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TeamMemberException extends Exception{
    public TeamMemberException() {
        super("对不起，您不是此行程用户");
    }
    @ResponseBody
    @ExceptionHandler(TeamMemberException.class)
    public JSONObject handleException(Exception e){
        return JSONUtils.fail(e);
    }
}
