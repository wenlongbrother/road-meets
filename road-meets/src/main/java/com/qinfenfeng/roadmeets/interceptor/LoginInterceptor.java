package com.qinfenfeng.roadmeets.interceptor;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

public class LoginInterceptor implements HandlerInterceptor{
    /**
     * 判断用户是否存在redis中
     * @param request
     * @param response
     * @return
     */
    private static RedisTemplate redisTemplate = new RedisTemplate();
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        response.setContentType("application/json;charset=utf-8");
        if(token == null){
//            System.out.println(1);
            return false;
        }
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(token);
        if(userInfo == null){
            return false;
        }
        // 刷新token
        redisTemplate.expire(token, 7, TimeUnit.DAYS);
        UserComponent.addUser(userInfo);
        return true;
    }
}
