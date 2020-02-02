package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ReleaseTeamException extends Exception{

    public ReleaseTeamException() {
        super("对不起，发布行程失败");
    }
    @ResponseBody
    @ExceptionHandler(ReleaseTeamException.class)
    public JSONObject handleException(Exception e){
        return JSONUtils.fail(e);
    }
}
