package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StartEndDto implements Serializable {
    private static final long serialVersionUID = 6064426935641783530L;
    private Long originId;
    private Long destinationId;
}
