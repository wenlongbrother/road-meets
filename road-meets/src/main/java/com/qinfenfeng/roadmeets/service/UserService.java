package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Wxservice接口
 * @author 蒋文龙
 * @date 2020/1/20
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
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

    UserInfoDto getUserInfoByUserId(Long userId) throws NoUserException;

    UserInfo getUserInfoByToken()throws NoUserException;
    int onlineNumber(LocationDto locationDto);
}
