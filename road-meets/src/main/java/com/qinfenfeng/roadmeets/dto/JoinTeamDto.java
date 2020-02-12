package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinTeamDto implements Serializable {
    private static final long serialVersionUID = 5027975078395472663L;
    private Long teamId;
    private String avatar;
}
