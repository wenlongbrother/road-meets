package com.qinfenfeng.roadmeets.dto;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserInfoDto
 * @author 蒋文龙
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto extends UserInfo implements Serializable {
    private String Token;
    private String SessionKey;
    private static final long serialVersionUID = -6803590988262278173L;

}
