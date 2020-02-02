package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLocationDto extends LocationDto implements Serializable {
    private static final long serialVersionUID = 3779646018065026091L;
    private long userId;
    private Double longitude;
    private Double latitude;

    public UserLocationDto(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

}
