package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateChatDto implements Serializable {
    private static final long serialVersionUID = 6355711959565031945L;
    private String nickName;
    private Long userId;
    private String content;
    private String scene;
    private Long friendId;
}
