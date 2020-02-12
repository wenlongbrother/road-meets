package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.GroupChatDto;
import com.qinfenfeng.roadmeets.dto.MessageDto;
import com.qinfenfeng.roadmeets.dto.PrivateChatDto;
import com.qinfenfeng.roadmeets.dto.TeamChatDto;
import com.qinfenfeng.roadmeets.mbg.model.Message;
import com.qinfenfeng.roadmeets.utils.exception.FriendException;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import com.qinfenfeng.roadmeets.utils.exception.TeamMemberException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface ChatService {

    void groupChat(GroupChatDto groupChatDto) throws NoUserException;

    void privateChat(PrivateChatDto privateChatDto) throws FriendException;

    void teamChat(TeamChatDto teamChatDto) throws TeamMemberException;

    List<MessageDto> historyMessage(String scene);
}
