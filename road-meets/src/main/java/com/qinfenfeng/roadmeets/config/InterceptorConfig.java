package com.qinfenfeng.roadmeets.config;

import com.qinfenfeng.roadmeets.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 拦截器配置类
 */
//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/","/druid/*","/loginWx, /error"} ;
        registry.addInterceptor(new LoginInterceptor())                   //添加拦截器
                .excludePathPatterns(exclude)//对应的不拦截的请求
                .addPathPatterns("/**")  ;
    }
}

