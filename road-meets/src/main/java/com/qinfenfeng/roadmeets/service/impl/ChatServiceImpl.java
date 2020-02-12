package com.qinfenfeng.roadmeets.service.impl;

import com.qinfenfeng.roadmeets.dto.GroupChatDto;
import com.qinfenfeng.roadmeets.dto.MessageDto;
import com.qinfenfeng.roadmeets.dto.PrivateChatDto;
import com.qinfenfeng.roadmeets.dto.TeamChatDto;
import com.qinfenfeng.roadmeets.dto.vo.JoinTeamVo;
import com.qinfenfeng.roadmeets.mbg.mapper.FriendMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.MessageMapper;
import com.qinfenfeng.roadmeets.mbg.mapper.TeamUserRelMapper;
import com.qinfenfeng.roadmeets.mbg.model.Friend;
import com.qinfenfeng.roadmeets.mbg.model.Message;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.mbg.model.TeamUserRel;
import com.qinfenfeng.roadmeets.service.ChatService;
import com.qinfenfeng.roadmeets.utils.common.GeoHashUtils;
import com.qinfenfeng.roadmeets.utils.exception.FriendException;
import com.qinfenfeng.roadmeets.utils.exception.JoinUserException;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import com.qinfenfeng.roadmeets.utils.exception.TeamMemberException;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Resource
    private GeoHashUtils geoHashUtil;
    @Value("${Team.distance}")
    private int distance;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private FriendMapper friendMapper;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private TeamUserRelMapper teamUserRelMapper;

    @Override
    public void groupChat(GroupChatDto groupChatDto) throws NoUserException {
        // 获取在范围内的所有userId
        List<Long> userIds = geoHashUtil.nearBySearchUser(distance, groupChatDto.getLongitude(), groupChatDto.getLatitude());
        for(int i = 0; i < userIds.size(); i++){
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(userIds.get(i)), "/topic/group",
                    groupChatDto);
        }
    }

    @Override
    public void privateChat(PrivateChatDto privateChatDto) throws FriendException {
        int i = friendMapper.selectByScene(dozerMapper.map(privateChatDto, Friend.class));
        if(i == 0){
            throw new FriendException();
        }
        Date date = new Date();
        Message message = new Message();
        message.setReaded((byte) 0);
        message.setContent(privateChatDto.getContent());
        message.setDeleted((byte) 0);
        message.setGmtCreate(date);
        message.setScene(privateChatDto.getScene());
        message.setSpeaker(privateChatDto.getUserId());
        // 把信息放入数据库
        messageMapper.insert(message);
        // 广播给用户
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(privateChatDto.getFriendId()), "/topic/private", privateChatDto);

    }

    @Override
    public void teamChat(TeamChatDto teamChatDto) throws TeamMemberException {
        Long toId = teamChatDto.getToId();
        Long userId = teamChatDto.getUserId();
        Long teamId = teamChatDto.getTeamId();
        // 先从redis获得行程申请
        JoinTeamVo joinTeamVo = (JoinTeamVo) redisTemplate.opsForHash().get("teamInfo:" + teamId, userId);
        // 如果没有申请过
        if(joinTeamVo == null){
            // 则从redis中获得行程信息
            Team team = (Team) redisTemplate.opsForValue().get("teamId:" + teamId);
            // 如果team不存在或者并不是该用户发布的则抛异常
            if(team == null || !userId.equals(team.getCreator())){
                throw new TeamMemberException();
            }
        }
        Date date = new Date();
        Message message = new Message();
        message.setReaded((byte) 0);
        message.setContent(teamChatDto.getContent());
        message.setDeleted((byte) 0);
        message.setGmtCreate(date);
        message.setScene(teamChatDto.getScene());
        message.setSpeaker(teamChatDto.getUserId());
        // 将消息放入数据库
        messageMapper.insert(message);
        // 将消息广播给用户
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(toId), "/topic/teamChat", teamChatDto);
    }

    @Override
    public List<MessageDto> historyMessage(String scene) {
        List<Message> messages = messageMapper.selectByScene(scene);
        List<MessageDto> messageDtos = new LinkedList<>();
        for (Message message:messages
             ) {
            messageDtos.add(dozerMapper.map(message, MessageDto.class));
        }
        return messageDtos;
    }

}
