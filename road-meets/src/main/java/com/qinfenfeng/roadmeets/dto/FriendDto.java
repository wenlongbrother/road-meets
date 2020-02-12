package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FriendDto implements Serializable {
    private static final long serialVersionUID = 2158003334609162364L;
    private String nickName;
    private String avatar;
    private Long teamId;
    private Long creator;
    private Date gmtCreate;
    private Long originId;
    private Long destinationId;
    protected Date targetTime;
    private Byte finished;
}
