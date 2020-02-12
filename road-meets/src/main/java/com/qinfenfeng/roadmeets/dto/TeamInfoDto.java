package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfoDto implements Serializable {
    private static final long serialVersionUID = -1587234573083856433L;
    private Long id;
    private Date gmtCreate;
    private Long creator;
    private String creatorNickName;
    private Long originId;
    private Long destinationId;
    protected Date targetTime;
    private Byte finished;
    private Long memberId;
    private String memberNickName;
}
