package com.qinfenfeng.roadmeets.interceptor;

import com.qinfenfeng.roadmeets.utils.exception.TokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(token != null){
            Object o = redisTemplate.opsForValue().get(token);
            if(o != null){
                // 把用户重新设置有效期
                redisTemplate.expire(token, 7, TimeUnit.DAYS);
                return true;
            }
            // 如果为空抛出异常TokenException
            throw new TokenException();
        }
        return true;
    }
}
