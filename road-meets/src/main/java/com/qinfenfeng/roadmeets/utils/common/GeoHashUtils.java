package com.qinfenfeng.roadmeets.utils.common;

import com.qinfenfeng.roadmeets.dto.TeamUserLocationDto;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * GeoHash的工具类
 */
@Component
public class GeoHashUtils {
    /**
     * 此处不适用@Autowired
     * If you add a @Bean of your own of any of the auto-configured types it will replace the default
     *  (except in the case of RedisTemplate the exclusion is based on the bean name ‘redisTemplate’ not its type).
     */
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    //GEO相关命令用到的KEY
    private final static String KEY_USER = "user_location_info";
    private final static String KEY_TEAM = "team_location_info";
    private final static String KEY_GROUP_CHAT = "group_chat_info";

    /**
     * 将用户位置放入redis
     * @param user
     * @return
     */
    public boolean saveUser(TeamUserLocationDto user) {
        Long flag = redisTemplate.opsForGeo().add(KEY_USER, new RedisGeoCommands.GeoLocation<>(
                user.getCreatorId(),
                new Point(user.getLongitude(), user.getLatitude()))
        );
        return flag != null && flag > 0;
    }

    /**
     * 将team位置放入redis
     * @param team
     * @return
     */
    public boolean saveTeam(TeamUserLocationDto team) {
        Long flag = redisTemplate.opsForGeo().add(KEY_TEAM, new RedisGeoCommands.GeoLocation<>(
                team.getTeamId(),
                new Point(team.getLongitude(), team.getLatitude()))
        );
        return flag != null && flag > 0;
    }

    /**
     * 把聊天室放入redis
     * @param user
     * @return
     */
    @Deprecated
    public boolean saveGroupChat(TeamUserLocationDto user){
        Long flag = redisTemplate.opsForGeo().add(KEY_GROUP_CHAT, new RedisGeoCommands.GeoLocation<>(
                user.getCreatorId(),
                new Point(user.getLongitude(), user.getLatitude()))
        );
        return flag != null && flag > 0;
    }
    /**
     * 根据当前位置获取附近指定范围内的用户
     * @param distance 指定范围 单位km ，可根据{@link Metrics} 进行设置
     * @param userLongitude 用户经度
     * @param userLatitude 用户纬度
     * @return
     */
    public List<Long> nearBySearchUser(double distance, double userLongitude, double userLatitude) {
        return helpSearch(distance, userLongitude, userLatitude, KEY_USER);
    }

    /**
     * 根据当前用户位置获取指定范围内行程
     * @param distance 指定范围 单位km ，可根据{@link Metrics} 进行设置
     * @param userLongitude 用户经度
     * @param userLatitude 用户纬度
     * @return
     */
    public List<Long> nearBySearchTeam(double distance, double userLongitude, double userLatitude){
        return helpSearch(distance, userLongitude, userLatitude, KEY_TEAM);
    }

    /**
     * 设计思路：如果用户范围内存在聊天室，则返回聊天室，如果不存在则创建一个返回
     * 根据设计图来看，最终决定在前端发送消息时附带坐标，根据坐标去发送给再范围内的人
     * @param distance
     * @param userLongitude
     * @param userLatitude
     * @return
     */
    @Deprecated
    public List<Long> nearBySearchGroup(double distance, double userLongitude, double userLatitude){
        return helpSearch(distance, userLongitude, userLatitude, KEY_GROUP_CHAT);
    }

    /**
     * 辅助函数用来获取附近范围的行程或用户
     * @param distance
     * @param longitude
     * @param latitude
     * @param key
     * @return
     */
    private List<Long> helpSearch(double distance, double longitude, double latitude, String key){
        List<Long> list = new ArrayList<>();
        // 1. GEORADIUS获取范围内附近的信息
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo().radius(key,
                new Circle(new Point(longitude, latitude), new Distance(distance, Metrics.KILOMETERS)),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().
                        includeDistance().
                        includeDistance().sortAscending());

        //2.收集信息，存入list
        List<GeoResult<RedisGeoCommands.GeoLocation<Object>>> content = results.getContent();
        content.forEach(a -> list.add(
                (Long) a.getContent().getName()));
        return list;
    }
}
