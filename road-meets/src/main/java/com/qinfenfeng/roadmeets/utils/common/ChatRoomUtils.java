package com.qinfenfeng.roadmeets.utils.common;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;
import com.qinfenfeng.roadmeets.utils.component.UserComponent;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomUtils {
    private static Map<Integer, UserInfo> chatRoom = new HashMap<>();

    /**
     * 将用户放入群聊
     * @param id
     * @param user
     */
    public static void put(Integer id, UserInfo user){
        chatRoom.put(id, user);
    }

    /**
     * 移除群聊
     * @param id
     */
    public static void remove(Integer id){
        chatRoom.remove(id);
    }
    public static void sendInfo(String text){
        UserInfo userInfo = UserComponent.getUserInfo();
        String nickName = userInfo.getNickName();

    }
}
