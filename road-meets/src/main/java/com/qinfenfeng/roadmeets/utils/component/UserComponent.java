package com.qinfenfeng.roadmeets.utils.component;

import com.qinfenfeng.roadmeets.mbg.model.UserInfo;

/**
 * 这个类记录了用户信息
 * @author 蒋文龙
 * @date 2020/1/21
 */

public class UserComponent {
    private static ThreadLocal<UserInfo> entryset = new ThreadLocal<>();
    /**
     * 插入用户
     * @param userInfo
     */
    public static void addUser(UserInfo userInfo){
        entryset.set(userInfo);
    }

    /**
     * 获得用户信息
     * @return
     */
    public static UserInfo getUserInfo(){
        return entryset.get();
    }

    /**
     * 获得用户ID
     * @return
     */
    public static Long getUserId(){
        return entryset.get().getId();
    }

    /**
     * 获得openId
     * @return
     */
    public static String getOpenId(){
        return entryset.get().getOpenIdMin();
    }

    /**
     * 获得unionId
     * @return
     */
    public static String getUnionId(){
        return entryset.get().getUnionId();
    }

    /**
     * 注销用户，并清除此次链接
     */
    public static void clear(){
        entryset.remove();
    }


}
