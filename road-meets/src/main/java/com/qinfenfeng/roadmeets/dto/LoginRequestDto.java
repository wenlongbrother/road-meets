package com.qinfenfeng.roadmeets.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto implements Serializable {
    private static final long serialVersionUID = 1245584352260151816L;
    private String jsCode;
    //偏移量
    private String iv;
    //密文
    private String encryptedData;

}
