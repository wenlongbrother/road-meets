package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdviceTeamDto implements Serializable {
    private static final long serialVersionUID = -3613164797510038542L;
    private Long id;
    private Date gmtCreate;
    private Long creator;
    protected Long originId;
    protected Long destinationId;
    protected Date targetTime;
    protected String scene;
}
