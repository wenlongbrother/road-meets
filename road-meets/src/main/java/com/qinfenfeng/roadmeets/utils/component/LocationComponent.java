package com.qinfenfeng.roadmeets.utils.component;

import com.qinfenfeng.roadmeets.dto.LocationDto;
import com.qinfenfeng.roadmeets.mbg.model.UserInfo;

public class LocationComponent {
    private static ThreadLocal<LocationDto> entryset = new ThreadLocal<>();

    /**
     * 获取用户的位置
     * @return
     */
    public static LocationDto getUserLocation(){
        return entryset.get();
    }

    /**
     * 删除用户位置
     */
    public static void removeLocation(){
        entryset.remove();
    }

    /**
     * 更新或者保存用户位置
     * @param locationDto 新的用户位置
     */
    public static void setLocation(LocationDto locationDto){
        entryset.set(locationDto);
    }
}
