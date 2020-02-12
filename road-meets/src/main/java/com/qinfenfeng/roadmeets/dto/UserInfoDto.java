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
public class UserInfoDto implements Serializable {
    private String Token;
    private Long id;
    private String nickName;
    private String avatarUrl;
    private Byte gender;
    private String phoneNum;
    private static final long serialVersionUID = -6803590988262278173L;

}
