package com.qinfenfeng.roadmeets.interceptor;

import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.TeamUserLocationDto;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.component.LocationComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdviceTeamInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(LocationComponent.getUserLocation() == null ){
            String token = request.getHeader("token");
            UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(token);
            TeamUserLocationDto teamUserLocationDto = (TeamUserLocationDto) redisTemplate.opsForValue().get("userLocation:" + userInfo.getId());
            LocationDto locationDto = new LocationDto(teamUserLocationDto.getLongitude(), teamUserLocationDto.getLatitude());
            LocationComponent.setLocation(locationDto);
            return true;
        }
        return false;
    }
}
