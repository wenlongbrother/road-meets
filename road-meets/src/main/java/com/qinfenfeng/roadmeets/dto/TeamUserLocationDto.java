package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeamUserLocationDto implements Serializable {
    private static final long serialVersionUID = 3779646018065026091L;
    private long creatorId;
    private long memberId;
    private Double longitude;
    private Double latitude;
    private long teamId;

    public TeamUserLocationDto(Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public TeamUserLocationDto(long creatorId) {
        this.creatorId = creatorId;
    }


    public TeamUserLocationDto(long teamId, Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.teamId = teamId;
    }
}
