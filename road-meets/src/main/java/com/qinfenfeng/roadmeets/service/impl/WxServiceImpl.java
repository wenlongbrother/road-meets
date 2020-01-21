package com.qinfenfeng.roadmeets.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.mbg.model.UserInfoExample;
import com.qinfenfeng.roadmeets.service.WxService;
import com.qinfenfeng.roadmeets.utils.common.HttpClientUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;


/**
 * Wxservice
 * @author 蒋文龙
 * @date 2020/1/20
 */
@Service
public class WxServiceImpl implements WxService {
    @Value("${weixin.appID}")
    private String appID ;
    @Value("${weixin.secret}")
    private String appsecret;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    Date date = new Date();

    UserInfoDto userInfoDto = new UserInfoDto();

    /**
     * 登录逻辑
     * @param jsCode
     * @return
     * @throws IOException
     */
    @Override
    public UserInfoDto loginService(String jsCode) throws IOException {
        //获得请求url
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + appID +
                "&secret=" + appsecret +
                "&js_code=" + jsCode +
                "&grant_type=authorization_code";
        JSONObject jsonObject;
        // 发起请求并获得返回值
        jsonObject = HttpClientUtils.doGet(url);
        // 对errocode进行判断
        if(jsonObject.get("errcode").equals(40029)){
            throw new IOException("对不起，code 无效");
        }
        if(jsonObject.get("errcode").equals(45011)){
            throw new IOException("频率限制，每个用户每分钟100次");
        }
        // 优雅的对openid进行获取
        Optional<String> notNull = Optional.ofNullable(jsonObject.getString("openid"));
        String openid = notNull.orElseThrow(()->new IOException("对不起，获取openid失败"));
        if(isExistInDB(openid)){

        }
        else {
            UserInfo userInfo = new UserInfo();
            userInfo.setGmtCreate(date);
            userInfo.setGmtModified(date);
            userInfoMapper.insert(userInfo);
        }


    }

    /**
     * 辅助方法，判断在数据库中是否存在此用户
     * @param openId
     * @return
     */
    private boolean isExistInDB(String openId){
        // 从redis中查询是否存在此用户
        String userInfoRedis = redisTemplate.opsForValue().get("open_id_min");
        if(userInfoRedis == null){
            //从数据库中查询是否存在此用户
            UserInfo userInfoMySQL = userInfoMapper.selectByOpenId(openId);
            if(userInfoMySQL == null){
                return false;
            }
        }

        return true;
    }


}
