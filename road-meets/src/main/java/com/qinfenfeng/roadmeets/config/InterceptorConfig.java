package com.qinfenfeng.roadmeets.config;

import com.qinfenfeng.roadmeets.interceptor.AdviceTeamInterceptor;
import com.qinfenfeng.roadmeets.interceptor.SessionInterceptor;
import com.qinfenfeng.roadmeets.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 拦截器配置类
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Resource
    private AdviceTeamInterceptor adviceTeamInterceptor;
    @Resource
    public TokenInterceptor tokenInterceptor;
//    @Resource
//    public SessionInterceptor sessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/","/druid/*","/loginWx", "/error", "/test", "/app/**"} ;

        registry.addInterceptor(tokenInterceptor)                   //添加拦截器
                .excludePathPatterns(exclude)//对应的不拦截的请求
                .addPathPatterns("/**")  ;

        String[] advicePath = {"/adviceTeam", "/releaseTeam", "/enterChatRoom"};
        registry.addInterceptor(adviceTeamInterceptor)
                .addPathPatterns(advicePath);

//        String[] tokenPath = {"/app/**", "/gs-guide-websocket/**", "/user/**"};
//        registry.addInterceptor(sessionInterceptor)
//                .addPathPatterns(tokenPath);
    }

}

