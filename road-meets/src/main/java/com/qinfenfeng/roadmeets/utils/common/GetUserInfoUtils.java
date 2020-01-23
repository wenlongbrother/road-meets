package com.qinfenfeng.roadmeets.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.utils.exception.CodeExcpetion;
import com.qinfenfeng.roadmeets.utils.exception.FrequencyException;
import org.apache.commons.codec.binary.Base64;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class GetUserInfoUtils {
    @Value("${weixin.appID}")
    private static String appID;

    @Value("${weixin.secret}")
    private static String appsecret;

    /**
     * 解密返回数据
     * @param session_key
     * @param encryptedData
     * @param iv
     * @return
     */
    public JSONObject descryptUserInfo(String session_key,String encryptedData,String iv) {
        if(encryptedData == null || iv == null){
            return  null;
        }
        Map<String, String> resUserInfo = new HashMap<>();
        // 被加密的数据
        byte[] dataByte = Base64.decodeBase64(encryptedData);
        // 加密秘钥
        byte[] aeskey = Base64.decodeBase64(session_key);
        // 偏移量
        byte[] ivByte = Base64.decodeBase64(iv);
        String newuserInfo = "";
        try {
            AesUtils aes = new AesUtils();
            byte[] resultByte = aes.decrypt(dataByte, aeskey, ivByte);
            if (null != resultByte && resultByte.length > 0) {
                newuserInfo = new String(resultByte, "UTF-8");
                JSONObject userInfo = JSONObject.parseObject(newuserInfo);
                System.out.println(userInfo);
                return userInfo;
            }
        } catch (Exception e) {
            System.out.printf("解密异常!检查解密数据 {}", newuserInfo, e);
        }
        return null;
    }

    /**
     * 获得解密文件中的数据
     * @return
     */
    public Map<String, String> getUserInfo(String jsCode, String encryptedData, String iv) throws Exception {
        Map<String, String> UserInfoMap = new HashMap<>();
        //获得请求url
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + appID +
                "&secret=" + appsecret +
                "&js_code=" + jsCode +
                "&grant_type=authorization_code";
        // 发起请求并获得返回值
        JSONObject jsonObject = HttpClientUtils.doGet(url);
        // 对errocode进行判断
        if (jsonObject.get("errcode").equals(40029)) {
            throw new CodeExcpetion();
        }
        if (jsonObject.get("errcode").equals(45011)) {
            throw new FrequencyException();
        }
        // 优雅的获取session_key
        Optional<String> notNullSessionKey = Optional.ofNullable(jsonObject.getString("session_key"));

        String SessionKey = notNullSessionKey.orElseThrow(() -> new IOException("对不起，获取session_key失败"));
        // 优雅的对openid进行获取

        Optional<String> notNullOpenId = Optional.ofNullable(jsonObject.getString("openid"));
        String openId = notNullOpenId.orElseGet(() -> (String) descryptUserInfo(SessionKey, encryptedData, iv).get("openid"));

        // 优雅的对UnionId进行获取
        Optional<String> notNullUnionId = Optional.ofNullable(jsonObject.getString("unionid"));

        String UnionId = notNullUnionId.orElseGet(() -> (String) descryptUserInfo(SessionKey, encryptedData, iv).get("unionid"));
        // 获取用户头像
        String avatarUrl = (String) descryptUserInfo(SessionKey, encryptedData, iv).get("avatarUrl");
        // 获取用户昵称
        String nickName = (String) descryptUserInfo(SessionKey, encryptedData, iv).get("nickName");
        // 获取用户性别
        String gender = (String) descryptUserInfo(SessionKey, encryptedData, iv).get("gender");
        UserInfoMap.put("openId", openId);
        UserInfoMap.put("UnionId", UnionId);
        UserInfoMap.put("SessionKey", SessionKey);
        UserInfoMap.put("avatarUrl", avatarUrl);
        UserInfoMap.put("nickName", nickName);
        UserInfoMap.put("gender", gender);
        return UserInfoMap;
    }

}
