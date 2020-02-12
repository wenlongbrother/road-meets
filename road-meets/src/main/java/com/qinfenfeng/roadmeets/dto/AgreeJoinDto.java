package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgreeJoinDto implements Serializable {
    private static final long serialVersionUID = 6768011471524856704L;
    private Long teamId;
    private Long memberId;
    private String avatar;
}
