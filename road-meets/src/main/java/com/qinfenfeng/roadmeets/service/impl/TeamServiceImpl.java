package com.qinfenfeng.roadmeets.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.dto.TeamDto;
import com.qinfenfeng.roadmeets.dto.UserLocationDto;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamMapper;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.TeamService;
import com.qinfenfeng.roadmeets.utils.common.GeoHashUtil;
import com.qinfenfeng.roadmeets.utils.component.LocationComponent;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import com.qinfenfeng.roadmeets.utils.exception.ReleaseTeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    TeamMapper teamMapper;
    @Value("${Team.passTime}")
    int passTime;
    @Value("${Team.distance}")
    int distance;
    /**
     * 发布形成
     * @param token
     * @param teamDto
     * @return
     * @throws ReleaseTeamException
     * @throws NoUserException
     */
    @Override
    public boolean releaseTeam(String token, TeamDto teamDto) throws ReleaseTeamException, NoUserException {
        UserInfo user = (UserInfo) redisTemplate.opsForValue().get(token);
        Date date = new Date();
        Team team = teamDto;
        team.setGmtCreate(date);
        team.setGmtModified(date);
        Optional<Long> userId = Optional.ofNullable(user.getId());
        team.setCreator(userId.orElseThrow(NoUserException::new));
        Long i = Long.valueOf(teamMapper.insert(team));
        team.setId(i);
        redisTemplate.opsForValue().set(userId,team,passTime, TimeUnit.DAYS);
        return true;
    }

    /**
     * 推荐行程
     * @return
     * @throws NoUserException
     */
    @Override
    public List<Team> adviceTeam() throws NoUserException {
        LocationDto location = LocationComponent.getUserLocation();
        GeoHashUtil geoHashUtil = new GeoHashUtil();
        // 获得范围内的所有人员
        List<UserLocationDto> userlocationDtos = geoHashUtil.nearBySearch(distance, location.getLongitude(), location.getLatitude());
        List<Team> teams = new ArrayList<>();
        for(UserLocationDto userLocation : userlocationDtos){
            // 从redis中根据用户Id获取行程列表并返回
            teams.add((Team) redisTemplate.opsForValue().get(userLocation.getUserId()));
        }
        return teams;
    }
}
