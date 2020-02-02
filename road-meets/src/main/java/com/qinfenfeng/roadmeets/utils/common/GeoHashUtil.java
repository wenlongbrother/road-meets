package com.qinfenfeng.roadmeets.utils.common;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.UserInfoDto;
import com.qinfenfeng.roadmeets.dto.UserLocationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * GeoHash的工具类
 */
public class GeoHashUtil {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    //GEO相关命令用到的KEY
    private final static String KEY = "user_location_info";
    public boolean save(UserLocationDto user) {
        Long flag = redisTemplate.opsForGeo().add(KEY, new RedisGeoCommands.GeoLocation<>(
                user.getUserId(),
                new Point(user.getLongitude(), user.getLatitude()))
        );
        return flag != null && flag > 0;
    }
    /**
     * 根据当前位置获取附近指定范围内的用户
     * @param distance 指定范围 单位km ，可根据{@link Metrics} 进行设置
     * @param userLng 用户经度
     * @param userLat 用户纬度
     * @return
     */
    public List<UserLocationDto> nearBySearch(double distance, double userLng, double userLat) {
        List<UserLocationDto> users = new ArrayList<>();
        // 1. GEORADIUS获取范围内附近的信息
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius(KEY,
                new Circle(new Point(userLng, userLat), new Distance(distance, Metrics.KILOMETERS)),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().
                        includeDistance().
                        includeDistance().sortAscending());
        //2.收集信息，存入list
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = results.getContent();
        content.forEach(a -> users.add(
                new UserLocationDto(a.getContent().getPoint().getX(),
                        a.getContent().getPoint().getY())));
        return users;
    }
}
