package com.qinfenfeng.roadmeets.dto;

import com.qinfenfeng.roadmeets.mbg.model.Team;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseTeamDto implements Serializable {
    private Long id;
    private Long originId;
    private Long destinationId;
    private Date targetTime;
    private String scene;

    private static final long serialVersionUID = 1372585171019098104L;
}
