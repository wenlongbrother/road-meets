package com.qinfenfeng.roadmeets.dto.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamInfoVo implements Serializable {
    private Long teamId;
    private Byte userType;
}
