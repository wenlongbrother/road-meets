package com.qinfenfeng.roadmeets.service;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;

import java.io.IOException;

/**
 * Wxservice接口
 * @author 蒋文龙
 * @date 2020/1/20
 */
public interface WxService {
    /**
     * 登录接口
     * @param jsCode
     * @return
     */
    UserInfoDto loginService(String jsCode) throws IOException;

}
