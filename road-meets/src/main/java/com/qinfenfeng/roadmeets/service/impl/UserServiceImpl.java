package com.qinfenfeng.roadmeets.service.impl;


import com.qinfenfeng.roadmeets.dto.LoginRequestDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.GetUserInfoUtils;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Wxservice
 * @author 蒋文龙
 * @date 2020/1/20
 */
@Service
public class UserServiceImpl implements UserService {
    @Value("${TokenPassTime}")
    private long tokenPassTime;
    @Autowired
    GetUserInfoUtils getUserInfoUtils;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private TokenComponent tokenComponent;

    private Date date = new Date();

    private UserInfoDto userInfoDto;

    private UserInfo userInfo;

    private String token;

    // 这个变量的作用是保存第一次登陆后的用户实力，这样第一次保存后，就无需再次去entryset中查询
    private UserInfo userInfoFist;

//    HashMap<String, UserInfo> userInfoHashMap = new HashMap<>();

    /**
     * 登录逻辑
     *
     * @param loginRequestDto
     * @return
     * @throws IOException
     */
    @Override
    public UserInfoDto loginService(LoginRequestDto loginRequestDto) throws Exception {
        String jsCode = loginRequestDto.getJsCode();
        String encryptedData = loginRequestDto.getEncryptedData();
        String iv = loginRequestDto.getIv();
        if(jsCode == null || jsCode.length() == 0){
            throw new RuntimeException("对不起, js_code为空");
        }
        Map<String, String> userInfoMap= getUserInfoUtils.getUserInfo(jsCode, encryptedData, iv);
        String openId = userInfoMap.get("openid");
        String unionId = userInfoMap.get("unionid");
        String SessionKey = userInfoMap.get("SessionKey");
        Byte gender = Byte.valueOf(userInfoMap.get("gender"));
        String nickName = userInfoMap.get("nickName");
        String avatarUrl = userInfoMap.get("avatarUrl");
        if (isExistInDB(openId)) {
            // 如果存在获取
            userInfo = userInfoFist;
        } else {
            userInfo = new UserInfo();
            userInfo.setGmtCreate(date);
            userInfo.setGmtModified(date);
            userInfo.setOpenIdMin(openId);
            userInfo.setAvatarUrl(avatarUrl);
            userInfo.setUnionId(unionId);
            userInfo.setNickName(nickName);
            userInfo.setGender(gender);
            userInfoMapper.insert(userInfo);
        }
        // 利用Dozer进行bean到dto的转换
        userInfoDto = dozerMapper.map(userInfo, UserInfoDto.class);
        userInfoDto.setSessionKey(SessionKey);
        userInfoDto.setToken(token);
        return userInfoDto;
    }

    /**
     * 辅助方法，判断在数据库中是否存在此用户
     *
     * @param openId
     * @return
     */
    private boolean isExistInDB(String openId) {
            //从数据库中查询是否存在此用户
            UserInfo userInfoMySQL = userInfoMapper.selectByOpenId(openId);
            if (userInfoMySQL != null) {
                //使用userInfo的Id生成为期七天的Token
                try {
                    token = tokenComponent.createJWTToken(userInfoMySQL.getId(), userInfoMySQL.getNickName(), tokenPassTime);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                userInfoFist = userInfoMySQL;
                redisTemplate.opsForValue().set(token, userInfoMySQL, 7, TimeUnit.DAYS);
                return true;
            }
        return false;
    }

}