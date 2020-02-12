package com.qinfenfeng.roadmeets.utils.exception;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MonitorException extends Exception{
    public MonitorException() {
        super("对不起，您不是队长");
    }
    @ResponseBody
    @ExceptionHandler(MonitorException.class)
    public JSONObject handleException(Exception e){
        return JSONUtils.fail(e);
    }
}
