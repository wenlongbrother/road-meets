package com.qinfenfeng.roadmeets.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamUserRelVo implements Serializable {

    private static final long serialVersionUID = -181421090661258070L;
    private Long monitorId;
    private Long memberId;
    private Long teamId;
    private Byte type;
}
