package com.qinfenfeng.roadmeets.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.*;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.UserService;
import com.qinfenfeng.roadmeets.utils.common.GeoHashUtils;
import com.qinfenfeng.roadmeets.utils.common.GetUserInfoUtils;
import com.qinfenfeng.roadmeets.utils.common.HttpClientUtils;
import com.qinfenfeng.roadmeets.utils.component.LocationComponent;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;
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
    // 将这个工具类注入进来，这个工具类与其他工具类不同
    // 使用了@Resource注解，因此必须要将其放到IOC容器，使用@Resource的方式注入
    @Resource
    private GeoHashUtils geoHashUtil;


    // 这个变量的作用是保存第一次登陆后的用户实力，这样第一次保存后，就无需再次去entryset中查询
    private UserInfo userInfoFist;

    // 用来保存token
    private String token;

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
        // 从解密文件中获取以下信息
        Map<String, String> userInfoMap= getUserInfoUtils.getUserInfo(jsCode, encryptedData, iv);
        String openId = userInfoMap.get("openId");
        String unionId = userInfoMap.get("unionId");
//        String SessionKey = userInfoMap.get("SessionKey");
        Byte gender = Byte.valueOf(userInfoMap.get("gender"));
        String nickName = userInfoMap.get("nickName");
        String avatarUrl = userInfoMap.get("avatarUrl");
        if (isExistInDB(openId)) {
            // 如果存在获取
            userInfo = userInfoFist;
        } else {
            // 如果不存在就插入数据库
            userInfo = new UserInfo();
            userInfo.setGmtCreate(date);
            userInfo.setGmtModified(date);
            userInfo.setOpenIdMin(openId);
            userInfo.setAvatarUrl(avatarUrl);
//            userInfo.setUnionId(unionId);
            userInfo.setNickName(nickName);
            userInfo.setGender(gender);
            userInfo.setDeleted((byte) 0);
            userInfoMapper.insert(userInfo);
            token = tokenComponent.createJWTToken(userInfo.getId(), nickName, tokenPassTime);
            redisTemplate.opsForValue().set(token, userInfo, 7, TimeUnit.DAYS);
            redisTemplate.opsForValue().set(userInfo.getId(), userInfo, 7, TimeUnit.DAYS);
        }
        // 利用Dozer进行bean到dto的转换
        userInfoDto = dozerMapper.map(userInfo, UserInfoDto.class);
        userInfoDto.setToken(token);
        return userInfoDto;
    }
    /**
     * 安卓登录逻辑
     *
     * @param code
     * @return
     * @throws IOException
     */
    @Override
    public UserInfoDto loginAndroidService(String code) throws Exception {
        LoginAndroidDTO loginAndroidDTO=new LoginAndroidDTO();
        loginAndroidDTO.setCode(code);
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?" + "appid=" + loginAndroidDTO.getAppid() + "&secret=" + loginAndroidDTO.getSecret() + "&code=" + code + "&grant_type=authorization_code";
        if(code == null || code.length() == 0){
            throw new RuntimeException("对不起, code为空");
        }
        String accessToken=null;
        String openId=null;
        try {
            JSONObject wechatAccessToken = HttpClientUtils.doGet(url);
            accessToken = wechatAccessToken.getString("access_token");
            openId = wechatAccessToken.getString("openid");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Map<String,String> userInfoMap= getUserInfoUtils.getUserInfoAndroid(accessToken,openId);
//        String openId = userInfoMap.get("openId");
        String unionId = userInfoMap.get("unionId");
//        String SessionKey = userInfoMap.get("SessionKey");
        Byte gender = Byte.valueOf(userInfoMap.get("gender"));
        String nickName = userInfoMap.get("nickName");
        String avatarUrl = userInfoMap.get("avatarUrl");
        if (isExistInDB(openId)) {
            // 如果存在获取
            userInfo = userInfoFist;
        } else {
            // 如果不存在就插入数据库
            userInfo = new UserInfo();
            userInfo.setGmtCreate(date);
            userInfo.setGmtModified(date);
            userInfo.setOpenIdMin(openId);
            userInfo.setAvatarUrl(avatarUrl);
//            userInfo.setUnionId(unionId);
            userInfo.setNickName(nickName);
            userInfo.setGender(gender);
            userInfo.setDeleted((byte) 0);
            userInfoMapper.insert(userInfo);
            token = tokenComponent.createJWTToken(userInfo.getId(), nickName, tokenPassTime);
            redisTemplate.opsForValue().set(token, userInfo, 7, TimeUnit.DAYS);
            redisTemplate.opsForValue().set(userInfo.getId(), userInfo, 7, TimeUnit.DAYS);
        }
        // 利用Dozer进行bean到dto的转换
        userInfoDto = dozerMapper.map(userInfo, UserInfoDto.class);
        userInfoDto.setToken(token);
        return userInfoDto;
    }

    /**
     * 用户位置逻辑
     * @param token Token
     * @param longitude 经度
     * @param latitude  纬度
     * @return
     */
    @Override
    public boolean userLocation(String token, Double longitude, Double latitude) {
        LocationDto locationDto = new LocationDto();
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(token);
        long userId = userInfo.getId();
        locationDto.setLongitude(longitude);
        locationDto.setLatitude(latitude);
//        redisTemplate.opsForValue().set("userLocation" + userId,locationDto,30,TimeUnit.MINUTES);
        //更新geo用户位置
        saveUserLocationIntoPlace(userId,longitude,latitude);
        return true;
    }

    /**
     * 查看用户的信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoDto getUserInfoByUserId(Long userId) throws NoUserException {
        UserInfo user = (UserInfo) redisTemplate.opsForValue().get(userId);
        if(userInfo == null){
            user = userInfoMapper.selectByPrimaryKey(userId);
            if(user == null){
                throw new NoUserException();
            }
        }
        UserInfoDto userInfoDto = dozerMapper.map(user, UserInfoDto.class);
        return userInfoDto;
    }

    /**
     * 通过token获得用户信息
     * @return
     * @throws NoUserException
     */
    @Override
    public UserInfo getUserInfoByToken() throws NoUserException {
        UserInfo userInfo = UserComponent.getUserInfo();
        return userInfo;
    }

    @Override
    public int onlineNumber(LocationDto locationDto) {
        List<Long> longs = geoHashUtil.nearBySearchUser(3, locationDto.getLongitude(), locationDto.getLatitude());
        return longs.size();
    }

    /**
     * 辅助函数用来保存用户进入geoHash和entryset
     * @param userId 用户的Id
     * @param longitude 经度
     * @param latitude  纬度
     */
    private void saveUserLocationIntoPlace(long userId,Double longitude,Double latitude){

        LocationDto location = new LocationDto(longitude, latitude);
        // 这个地方使用方便
        TeamUserLocationDto userLocation = new TeamUserLocationDto(userId, longitude, latitude);
        geoHashUtil.saveUser(userLocation);
        // 放入entryset中
        LocationComponent.setLocation(location);
        redisTemplate.opsForValue().set("userLocation:" + userId, userLocation);
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
                    userInfoFist = userInfoMySQL;
                    UserComponent.addUser(userInfoMySQL);
                    redisTemplate.opsForValue().set(token, userInfoMySQL, 7, TimeUnit.DAYS);
                    redisTemplate.opsForValue().set(userInfoMySQL.getId(), userInfoMySQL, 7, TimeUnit.DAYS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        return false;
    }



}