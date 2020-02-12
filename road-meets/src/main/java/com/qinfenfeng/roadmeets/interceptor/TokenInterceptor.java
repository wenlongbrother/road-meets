package com.qinfenfeng.roadmeets.interceptor;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * 用来判断用户请求头是否有token
 */
@Component
public class TokenInterceptor implements HandlerInterceptor{
    /**
     * 判断用户是否存在redis中
     * @param request
     * @param response
     * @return
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 首先在ThreadLocal中判断是否可以获取用户
        if(UserComponent.getUserInfo() != null){
            return true;
        }
        String token = request.getHeader("token");
        response.setContentType("application/json;charset=utf-8");
        if(token == null){
            throw new NoUserException();
        }
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(token);
        if(userInfo == null){
            throw new NoUserException();
        }
        // 刷新token
        redisTemplate.expire(token, 7, TimeUnit.DAYS);
        redisTemplate.expire(userInfo.getId(), 7, TimeUnit.DAYS);
        UserComponent.addUser(userInfo);
        return true;
    }
}
