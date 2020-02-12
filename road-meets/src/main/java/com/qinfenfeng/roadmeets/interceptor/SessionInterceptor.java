package com.qinfenfeng.roadmeets.interceptor;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用来判断用户是否有session,仅仅在连接进聊天室的时候拦截一下
 * 在websocket中可以考虑使用token，也可以考虑使用session
 */
//@Component
public class SessionInterceptor implements HandlerInterceptor {
    @Autowired
    RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从容器中获得用户信息
        UserInfo userInfo = UserComponent.getUserInfo();
        // 获得token
        String token = request.getHeader("token");
        // 将用户信息保存到session中
        HttpSession session = request.getSession();
        // 如果为空则保存进session
        if(session == null){
            session.setAttribute(token, userInfo.getId());
        }
        return false;
    }
}
