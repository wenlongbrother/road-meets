package com.qinfenfeng.roadmeets.dto;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String jsCode;
    //偏移量
    private String iv;
    //密文
    private String encryptedData;

}
