package com.qinfenfeng.roadmeets.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginAndroidDTO {
    @Value("${weixin.appID}")
    private String appid;
    @Value("${weixin.secret}")
    private String secret;
    private String code;
    private String grant_type="authorization_code";

}
