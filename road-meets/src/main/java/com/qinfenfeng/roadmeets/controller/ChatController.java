package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.GroupChatDto;
import com.qinfenfeng.roadmeets.dto.MessageDto;
import com.qinfenfeng.roadmeets.dto.PrivateChatDto;
import com.qinfenfeng.roadmeets.dto.TeamChatDto;
import com.qinfenfeng.roadmeets.service.ChatService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
@Slf4j
@RestController
public class ChatController {
    @Autowired
    ChatService chatService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    /**
     * 发送群聊消息
     * @param groupChatDto
     * @return
     * @throws NoUserException
     */
    @MessageExceptionHandler(Exception.class)
    @MessageMapping("/groupChat")
    public void groupChat(GroupChatDto groupChatDto) throws NoUserException {
        chatService.groupChat(groupChatDto);
    }

    @MessageExceptionHandler(Exception.class)
    @MessageMapping("/privateChat")
    public void privateChat(PrivateChatDto message) throws Exception {
        chatService.privateChat(message);
    }

    @MessageExceptionHandler(Exception.class)
    @MessageMapping("/teamChat")
    public void teamChat(TeamChatDto teamChatDto) throws Exception{
        chatService.teamChat(teamChatDto);
    }

    @GetMapping("/historyMessage")
    public JSONObject historyMessage(String scene){
        return JSONUtils.success(chatService.historyMessage(scene));
    }
}
