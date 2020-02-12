package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluateDto implements Serializable {
    private static final long serialVersionUID = -834868566390097078L;
    private String scene;
    private Long teamId;
    private Byte evaluate;
}
