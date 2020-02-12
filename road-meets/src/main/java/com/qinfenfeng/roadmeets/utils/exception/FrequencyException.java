package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class FrequencyException extends Exception{
    public FrequencyException() {
        super("频率限制，每个用户每分钟100次");
    }
    @ResponseBody
    @ExceptionHandler(CodeExcpetion.class)
    public JSONObject handleException(Exception e){
        JSONObject jsonObject = JSONUtils.fail(e);
        return jsonObject;
    }
}
