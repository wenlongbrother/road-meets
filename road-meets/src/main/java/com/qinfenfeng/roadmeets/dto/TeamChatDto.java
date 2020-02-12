package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamChatDto implements Serializable {
    private static final long serialVersionUID = 2094448695092271554L;
    private String nickName;
    private Long userId;
    private String content;
    private String scene;
    private Long toId;
    private Long teamId;
}
