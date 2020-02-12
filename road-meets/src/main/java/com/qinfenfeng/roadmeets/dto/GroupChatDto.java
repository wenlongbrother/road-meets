package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatDto implements Serializable {

    private static final long serialVersionUID = -2407798184484365065L;
    private String nickName;
    private String userId;
    private String content;
    private Double longitude;
    private Double latitude;
}
