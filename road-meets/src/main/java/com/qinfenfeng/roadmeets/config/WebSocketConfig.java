package com.qinfenfeng.roadmeets.config;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.security.auth.login.LoginException;
import java.security.Principal;
import java.util.Map;

@Configuration
// @EnableWebSocketMessageBroker注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，
// 这时候控制器（controller）
// 开始支持@MessageMapping,就像是使用@requestMapping一样。
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 请求连接时的地址，也就是进入聊天的地址
        registry.addEndpoint("/gs-guide-websocket")
                .addInterceptors(myHandshakeInterceptor())
                .setHandshakeHandler(myDefaultHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS();
    }
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 广播所有订阅此连接的用户，他是服务器广播消息的基础路径
        registry.enableSimpleBroker("/topic");
        // 客户端向服务端发送时的主题上面需要加"/app"作为前缀， 广播时的前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 一对一用户时的前缀，无论群聊还是单聊都需要他
        // 因为群聊也应该被理解为私聊，一种多对多的私聊
        registry.setUserDestinationPrefix("/user/");
    }

    private HandshakeInterceptor myHandshakeInterceptor(){
        return new HandshakeInterceptor() {
            /**
             * 握手前进行token鉴权
             * @param serverHttpRequest
             * @param serverHttpResponse
             * @param webSocketHandler
             * @param map
             * @return
             * @throws Exception
             */
            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;

                // 根据token认证用户，不通过返回拒绝握手
                String token = request.getServletRequest().getParameter("token");
                Principal user = new Principal() {
                    @Override
                    public String getName() {
                        // 从redis中获得用户的信息
                        UserInfo user = (UserInfo) redisTemplate.opsForValue().get(token);
                        // 返回用户id
                        assert user != null;
                        return String.valueOf(user.getId());
                    }
                };
                // 为空则不通过
                if("".equals(user.getName())){
                    return false;
                }
                map.put("user", user);
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

            }
        };
    }
    private DefaultHandshakeHandler myDefaultHandshakeHandler(){
        return new DefaultHandshakeHandler(){
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                return (Principal) attributes.get("user");
            }
        };
    }

    private Principal authenticate(String token){
        //TODO 实现用户的认证并返回用户信息，如果认证失败返回 null
        //用户信息需继承 Principal 并实现 getName() 方法，返回全局唯一值
        return null;
    }
}

