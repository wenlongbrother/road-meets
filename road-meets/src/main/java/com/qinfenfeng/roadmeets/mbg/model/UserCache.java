package com.qinfenfeng.roadmeets.mbg.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCache implements Serializable {
    private long id;
    private LocalDateTime expireTime;
}