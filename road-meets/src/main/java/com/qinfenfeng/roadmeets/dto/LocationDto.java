package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationDto implements Serializable {
    private static final long serialVersionUID = 7524324000535069526L;
    private Double longitude;
    private Double latitude;
}
