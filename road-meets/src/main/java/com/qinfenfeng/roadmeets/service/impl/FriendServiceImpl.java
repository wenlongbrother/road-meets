package com.qinfenfeng.roadmeets.service.impl;

import com.qinfenfeng.roadmeets.dto.EvaluateDto;
import com.qinfenfeng.roadmeets.dto.FriendDto;
import com.qinfenfeng.roadmeets.dto.TeamInfoDto;
import com.qinfenfeng.roadmeets.mbg.mapper.FriendMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamUserRelMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.UserInfoMapper;
import com.qinfenfeng.roadmeets.mbg.model.Friend;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.mbg.model.TeamUserRel;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.service.FriendService;
import com.qinfenfeng.roadmeets.utils.component.TokenComponent;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class FriendServiceImpl implements FriendService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TeamMapper teamMapper;
    @Autowired
    private TeamUserRelMapper teamUserRelMapper;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    // 用来保存friendId
    private Long friendId;

    /**
     * 评价行程
     * @param evaluateDto
     * @return
     */
    @Override
    public boolean evaluate(EvaluateDto evaluateDto) {
        // 获得用户信息
        UserInfo userInfo = UserComponent.getUserInfo();
        Long userId = userInfo.getId();
        Long teamId = evaluateDto.getTeamId();
        TeamUserRel teamUserRel = new TeamUserRel();
        teamUserRel.setTeamId(teamId);
        teamUserRel.setEvaluate(evaluateDto.getEvaluate());
        teamUserRel.setUserId(userId);
        // 如果对方好评
        boolean flag = ifPraise(teamId, userId);
        // 成为朋友
        if(flag && evaluateDto.getEvaluate() == 1){
            insertFriend(evaluateDto, userId);
        }
        teamUserRelMapper.updateEvaluteByTeamIdUserId(teamUserRel);
        return true;
    }

    /**
     * 获得好友信息
     * @return
     */
    @Override
    public List<FriendDto> getFriend() {
        UserInfo userInfo = UserComponent.getUserInfo();
        List<Friend> friends = friendMapper.selectByUserId(userInfo.getId());
        List<FriendDto> friendDtos = new LinkedList<>();
        for (Friend friend: friends
             ) {
                 // 从数据库中获得好友所发布的行程
                List<Team> teams = teamMapper.selectTeamByCreator(friend.getFriendUserId());
            for (Team team: teams
                 ) {
                // 先从redis中看是否能获取到好友的信息
                UserInfo friendInfo = (UserInfo) redisTemplate.opsForValue().get(friend.getFriendUserId());
                // 如果redis中没找到， 就去数据库找
                if(friendInfo == null){
                    friendInfo = userInfoMapper.selectByPrimaryKey(team.getCreator());
                }
                FriendDto friendDto = new FriendDto();
                friendDto.setAvatar(friendInfo.getAvatarUrl());
                friendDto.setNickName(friendInfo.getNickName());
                friendDto.setDestinationId(team.getDestinationId());
                // 如果行程未完结
                if(team.getFinished() == 0){
                    friendDto.setFinished(team.getFinished());
                }
                friendDto.setGmtCreate(team.getGmtCreate());
                friendDto.setOriginId(team.getOriginId());
                friendDto.setTeamId(team.getId());
                friendDto.setCreator(friend.getFriendUserId());
                friendDto.setTargetTime(team.getTargetTime());
                friendDto.setScene(team.getScene());
                if(team.getFinished() == 0){
                    friendDtos.add(friendDto);
                }
            }
        }
        return friendDtos;
    }

    /**
     * 辅助函数用来判断对方是否对自己好评，在这个地方，数据库设计的有问题
     * 原因如下
     * 如果对方和自己同时评价为好评
     * 则必然无法插入到数据库中
     * 原因是因为逻辑代码是先去数据库中查找对方是否有无好评，然后再根据结果来进行下一步操作
     * 也就是说如果自己和对方同时评价， 数据库中值为0(默认为0)，对于双方来说，对方都未评价
     * 因此则无法成功插入数据库
     * @param teamId 队伍Id
     * @param userId 用户Id
     * @return
     */
    private boolean ifPraise(Long teamId, Long userId){
        TeamUserRel teamUserRel = teamUserRelMapper.selectByTeamIdUserId(userId, teamId);
        if(teamUserRel != null){
            if(teamUserRel.getEvaluate() == 1){
                friendId = teamUserRel.getUserId();
                return true;
            }
        }
        return false;
    }

    /**
     * 辅助函数，插入数据库
     * @param evaluateDto
     * @param userId 用户Id
     */
    private void insertFriend(EvaluateDto evaluateDto, Long userId){
        Friend friend1 = new Friend();
        Date date = new Date();
        friend1.setDeleted((byte) 0);
        friend1.setFriendUserId(friendId);
        friend1.setGmtCreate(date);
        friend1.setGmtModified(date);
        friend1.setUserId(userId);
        friend1.setScene(evaluateDto.getScene());
        friendMapper.insert(friend1);
        Friend friend2 = new Friend();
        friend2.setGmtModified(date);
        friend2.setGmtCreate(date);
        friend2.setFriendUserId(userId);
        friend2.setScene(evaluateDto.getScene());
        friend2.setDeleted((byte) 0);
        friend2.setUserId(friendId);
        friendMapper.insert(friend2);
    }
}
