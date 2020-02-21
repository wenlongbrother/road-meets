package com.qinfenfeng.roadmeets.service.impl;

import com.qinfenfeng.roadmeets.dto.*;
import com.qinfenfeng.roadmeets.dto.vo.JoinTeamVo;
import com.qinfenfeng.roadmeets.dto.vo.TeamInfoVo;
import com.qinfenfeng.roadmeets.dto.vo.TeamUserRelVo;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamUserRelMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.mbg.model.TeamUserRel;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.TeamService;
import com.qinfenfeng.roadmeets.utils.common.GeoHashUtils;
import com.qinfenfeng.roadmeets.utils.component.LocationComponent;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import com.qinfenfeng.roadmeets.utils.exception.*;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamUserRelMapper teamUserRelMapper;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Resource
    private GeoHashUtils geoHashUtil;
    @Value("${Team.passTime}")
    private int passTime;
    @Value("${Team.distance}")
    private int distance;
    /**
     * 发布行程
     * @param releaseTeamDto
     * @return
     * @throws ReleaseTeamException
     * @throws NoUserException
     */
    @Override
    public boolean releaseTeam(ReleaseTeamDto releaseTeamDto) throws ReleaseTeamException, NoUserException {
        UserInfo user = UserComponent.getUserInfo();
        Date date = new Date();
        Team team = dozerMapper.map(releaseTeamDto, Team.class);
        // 先把地址信息放入数据库
        team.setGmtCreate(date);
        team.setGmtModified(date);
        team.setDeleted((byte) 0);
        team.setFinished((byte) 0);
        Optional<Long> userId = Optional.ofNullable(user.getId());
        Long userIdNotNull = userId.orElseThrow(NoUserException::new);
        team.setCreator(userIdNotNull);
        // 队伍Id
        teamMapper.insert(team); // 插入Team表
        Long i = team.getId();
        // 插入TeamUserRel表
        TeamUserRel teamMonitor = new TeamUserRel(date, date, i, userIdNotNull,(byte)3);
        teamUserRelMapper.insert(teamMonitor);
        // 放入redis
        redisTemplate.opsForValue().set("teamId:" + i, team,passTime, TimeUnit.DAYS);
        redisTemplate.opsForHash().put("teamUserId:" + userId, i, team);
        // 如果是同一个线程，则直接从ThreadLocal中获取Location，否则则从redis中获取
        LocationDto location = LocationComponent.getUserLocation();

        // 将此行程放入geoHash
        // 之所以要这样设计，是为了便于扩展，同时将行程与用户分隔开，可以使性能上升
        TeamUserLocationDto teamUserLocationDto = new TeamUserLocationDto(i, location.getLongitude(), location.getLatitude());
        geoHashUtil.saveTeam(teamUserLocationDto);
        return true;
    }

    /**
     * 推荐行程
     * @return
     * @throws NoUserException
     */
    @Override
    public List<AdviceTeamDto> adviceTeam() throws NoUserException {
        // 如果是同一个线程，则直接从ThreadLocal中获取Location，否则则从redis中获取
        LocationDto location = LocationComponent.getUserLocation();
        List<AdviceTeamDto> teams = helpAdviceTeam(location);
        return teams;
    }

    /**
     * 申请加入行程
     * @param joinTeamDto
     * @return
     * @throws NoTeamException
     */
    @Override
    public JoinTeamVo joinTeam(JoinTeamDto joinTeamDto) throws NoTeamException {
        // 获得teamId
        Long teamId = joinTeamDto.getTeamId();
        // 获得队员Id
        UserInfo member = UserComponent.getUserInfo();
        Long memberId = member.getId();
        // 获得头像url
        String avatar = joinTeamDto.getAvatar();
        // 获得要加入的队伍
        Team team = (Team) redisTemplate.opsForValue().get("teamId:" + teamId);
        // 如果没有队伍则抛出异常
        if (team == null){
            throw new NoTeamException();
        }
        // 获得队长Id
        Long monitorId = team.getCreator();
        JoinTeamVo joinTeamVo = new JoinTeamVo(teamId, monitorId, memberId, (byte) 1, avatar);
        Date date = new Date();
        // 将申请放入数据库
        TeamUserRel teamMember = new TeamUserRel(date, date ,teamId, memberId, (byte) 1);
        teamUserRelMapper.insert(teamMember);
        // 将申请放入redis
        redisTemplate.opsForHash().put("teamInfo:" + teamId, memberId, joinTeamVo);
        return joinTeamVo;
    }

    /**
     * 行程详细信息
     * @param teamId
     * @return
     */
    @Override
    public TeamInfoDto teamInfo(Long teamId) {
        // 先从redis中寻找行程
        Team team = (Team) redisTemplate.opsForValue().get("teamId:" + teamId);
        // 如果没有，则从数据库查找
        if(team == null){
            team = teamMapper.selectByPrimaryKey(teamId);
        }
        // 获得creator的id
        Long creator = team.getCreator();
        // 从数据库中得到用户的信息
        UserInfo creatorInfo = userInfoMapper.selectByPrimaryKey(creator);
        TeamInfoDto teamInfo = dozerMapper.map(team, TeamInfoDto.class);
        teamInfo.setCreatorNickName(creatorInfo.getNickName());
        // 从数据库中获得乘客的id

        TeamUserRel teamUserRelInfo = teamUserRelMapper.selectByTeamId(new TeamInfoVo(teamId, (byte) 2));
        // 如果没有乘客直接返回
        if(teamUserRelInfo == null){
            return teamInfo;
        }
        // 从数据库中获得成员昵称
        UserInfo memberInfo = userInfoMapper.selectByPrimaryKey(teamUserRelInfo.getUserId());
        teamInfo.setMemberId(memberInfo.getId());
        teamInfo.setMemberNickName(memberInfo.getNickName());
        return teamInfo;
    }

    /**
     * 同意申请
     * @param agreeJoinDto
     * @return
     */
    @Override
    public boolean agreeJoin(AgreeJoinDto agreeJoinDto) throws JoinUserException {
        Long memberId = agreeJoinDto.getMemberId();
        Long teamId = agreeJoinDto.getTeamId();
        Long monitorId = UserComponent.getUserId();
        // 从redis中获得关于申请者的信息
        JoinTeamVo joinTeamVo = (JoinTeamVo) redisTemplate.opsForHash().get("teamInfo:" + teamId, memberId);
        if(joinTeamVo == null){
            throw new JoinUserException();
        }
        joinTeamVo.setType((byte) 2);
        TeamUserRel teamUserRel = new TeamUserRel();
        teamUserRel.setType((byte) 2);
        teamUserRel.setUserId(agreeJoinDto.getMemberId());
        teamUserRel.setTeamId(teamId);
        // 修改数据库中相应的值
        teamUserRelMapper.updateTypeByTeamIdUserId(teamUserRel);
        // 修改redis中的值
        redisTemplate.opsForHash().put("teamInfo:" + teamId, memberId, joinTeamVo);
        // 修改数据库中其他申请者的值
        teamUserRelMapper.updateTypeByTeamIdAntiUserId(new TeamUserRelVo(monitorId, memberId, teamId, (byte) 0));
        // 获得redis中所有的申请者Id
        Collection<JoinTeamVo> values = helpGetJoinUserId(teamId);
        List<JoinTeamVo> otherJoins = values.stream().
                filter(otherJoin -> !otherJoin.getMemberId().equals(memberId)).
                collect(Collectors.toList());
        //  把其他申请者的状态改为失败
        for(JoinTeamVo otherJoin : otherJoins){
            otherJoin.setType((byte) 0);
            // 重新放入redis
            redisTemplate.opsForHash().put("teamInfo:" + teamId, otherJoin.getMemberId(), otherJoin);
        }
        return true;
    }

    /**
     * 获得所有申请者的信息
     * @param teamId
     * @return
     */
    @Override
    public Collection getJoinUserId(Long teamId) {
        Collection values = helpGetJoinUserId(teamId);
        return values;
    }

    @Override
    public boolean endTeam(Long teamId) throws MonitorException {
        UserInfo userInfo = UserComponent.getUserInfo();
        // 从redis中获得行程信息
        Team team = (Team) redisTemplate.opsForValue().get("teamId:" + teamId);
        // 如果是队长
        if(team.getCreator().equals(userInfo.getId())){
            // 将行程设为完成
            team.setFinished((byte) 1);
            // 将数据库中行程设为完成
            teamMapper.updateByPrimaryKey(team);
            // 再放入redis
            redisTemplate.opsForValue().set("teamId:" + teamId, team);
            redisTemplate.opsForHash().put("teamUserId:" + userInfo.getId(), teamId, team);
            return true;
        }
        else {
            throw new MonitorException();
        }
    }

    @Override
    public List<AdviceTeamDto> selectTeam(LocationDto locationDto) throws NoUserException {
        List<AdviceTeamDto> teams = helpAdviceTeam(locationDto);
        return teams;
    }

    @Override
    public Set<TeamUserRel> teamHistory() {
        UserInfo userInfo = UserComponent.getUserInfo();
        Set<TeamUserRel> teams = teamUserRelMapper.selectByUserId(userInfo.getId());

        return teams;
    }

    @Override
    public Long joinState(Long teamId) {
        UserInfo userInfo = UserComponent.getUserInfo();
        Long userId = userInfo.getId();
        JoinTeamVo type = (JoinTeamVo) redisTemplate.opsForHash().get("teamInfo:" + teamId, userId);
        return Long.valueOf(type.getType());
    }

    /**
     * 辅助函数用来获取范围内的行程
     * @param location
     * @return
     */
    private List<AdviceTeamDto> helpAdviceTeam(LocationDto location){
        List<Long> teamlocationDtos = geoHashUtil.nearBySearchTeam(distance, location.getLongitude(), location.getLatitude());
        List<AdviceTeamDto> teams = new ArrayList<>();
        for(Long teamId : teamlocationDtos){
            // 从redis中根据userId获取用户的teamId;
            Team team = (Team) redisTemplate.opsForValue().get("teamId:" + teamId);
            if(team.getDeleted() != 1 && team.getFinished() != 1 && !isFull(team)) {
                AdviceTeamDto adviceTeamDto = dozerMapper.map(team, AdviceTeamDto.class);
                adviceTeamDto.setFull(false);
                teams.add(adviceTeamDto);
            }
        }
        return teams;
    }

    /**
     * 辅助函数，用来辅助获得申请者id
     * @param teamId
     * @return
     */
    private Collection<JoinTeamVo> helpGetJoinUserId(Long teamId){
        Map entries = redisTemplate.opsForHash().entries("teamInfo:" + teamId);
        // 遍历所有的value并返回
        Collection<JoinTeamVo>values = entries.values();
        return values;
    }

    /**
     * 判断队伍是否满员
     * @param team
     * @return
     */
    private boolean isFull(Team team){
        Long teamId = team.getId();
        TeamUserRel teamUserRel = teamUserRelMapper.selectByTeamId(new TeamInfoVo(teamId, (byte) 2));
        return teamUserRel != null;
    }
}
