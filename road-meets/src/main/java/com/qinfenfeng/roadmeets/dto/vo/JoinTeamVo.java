package com.qinfenfeng.roadmeets.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinTeamVo implements Serializable {
    private static final long serialVersionUID = 1046783731967932654L;
    private Long teamId;
    private Long monitorId;
    private Long memberId;
    private Byte type;
    private String avatar;

    public JoinTeamVo(Long teamId, Long monitorId, Long memberId) {
        this.teamId = teamId;
        this.monitorId = monitorId;
        this.memberId = memberId;
    }
}
