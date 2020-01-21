package com.qinfenfeng.roadmeets.utils.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 它的作用是用来判断用户的请求来源是微信还是app
 */
//@Aspect
//@Component
public class SelectClientAop {
//    @Before("execution(* com.qinfenfeng.roadmeets.controller.UserController.*(..))")
//    public void beforeLogin(JoinPoint joinPoint) {
//        HttpServletRequest request = (HttpServletRequest) joinPoint.getArgs()[0];
//        String userAgent = request.getHeader("user-agent").toLowerCase();
//        ;
//        if (userAgent.indexOf("micromessenger") != -1) {
//            //微信
//
//        } else if (userAgent.indexOf("android") != -1) {
//            //安卓
//
//        }
//    }
}
