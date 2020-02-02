package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;

import java.io.IOException;
import java.math.BigDecimal;

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
    UserInfoDto loginService(LoginRequestDto loginRequestDto) throws Exception;

    /**
     * 用户位置接口
     * @param token Token
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    boolean userLocation(String token, Double longitude, Double latitude);
}
