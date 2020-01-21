package com.qinfenfeng.roadmeets.service;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Wxservice接口
 * @author 蒋文龙
 * @date 2020/1/20
 */
public interface UserService {
    /**
     * 登录接口
     * @param loginRequestDto
     * @return
     */
    UserInfoDto loginService(LoginRequestDto loginRequestDto) throws IOException;

}
