package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.*;
import com.qinfenfeng.roadmeets.dto.vo.JoinTeamVo;
import com.qinfenfeng.roadmeets.service.TeamService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
import com.qinfenfeng.roadmeets.utils.exception.JoinUserException;
import com.qinfenfeng.roadmeets.utils.exception.MonitorException;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import com.qinfenfeng.roadmeets.utils.exception.ReleaseTeamException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TeamController {
    @Autowired
    TeamService teamService;

    /**
     * 用户发布行程
     * @param request
     * @param releaseTeamDto
     * @return
     * @throws ReleaseTeamException
     * @throws NoUserException
     */
    @PostMapping("/releaseTeam")
    public JSONObject releaseTeam(HttpServletRequest request, @RequestBody ReleaseTeamDto releaseTeamDto) throws ReleaseTeamException,
            NoUserException {
        return JSONUtils.success(teamService.releaseTeam(releaseTeamDto));
    }

    /**
     * 系统推荐行程
     * @return
     * @throws NoUserException
     */
    @GetMapping("/adviceTeam")
    public JSONObject adviceTeam()throws NoUserException{
        return JSONUtils.success(teamService.adviceTeam());
    }

    /**
     * 加入行程
     * @param request
     * @param joinTeamDto
     * @return
     */
    @PostMapping("/joinTeam")
    public JSONObject joinTeam(HttpServletRequest request, @RequestBody JoinTeamDto joinTeamDto){
        return JSONUtils.success(teamService.joinTeam(joinTeamDto));
    }

    @GetMapping("/teamInfo")
    public JSONObject teamInfo(Long teamId){
        return JSONUtils.success(teamService.teamInfo(teamId));
    }

    @PostMapping("/endTeam")
    public JSONObject endTeam(@RequestBody EndTeamDto endTeamDto) throws MonitorException {
        return JSONUtils.success(teamService.endTeam(endTeamDto.getTeamId()));
    }

    @GetMapping("/selectTeam")
    public JSONObject selectTeam(LocationDto locationDto) throws NoUserException {
        return JSONUtils.success(teamService.selectTeam(locationDto));
    }

    @GetMapping("/historyTeam")
    public JSONObject historyTeam(){
        return JSONUtils.success(teamService.teamHistory());
    }

    @GetMapping("/joinState")
    public JSONObject joinState(Long teamId){
        return JSONUtils.success(teamService.joinState(teamId));
    }

    @GetMapping("/getJoinUserId")
    public JSONObject getJoinUserId(Long teamId){
        return JSONUtils.success(teamService.getJoinUserId(teamId));
    }

    @PostMapping("/agreeJoin")
    public JSONObject agreeJoin(@RequestBody AgreeJoinDto agreeJoinDto) throws JoinUserException {
        return JSONUtils.success(teamService.agreeJoin(agreeJoinDto));
    }
}
