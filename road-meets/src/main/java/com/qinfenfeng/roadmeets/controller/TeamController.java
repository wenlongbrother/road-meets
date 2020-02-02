package com.qinfenfeng.roadmeets.controller;

import com.alibaba.fastjson.JSONObject;
import com.qinfenfeng.roadmeets.dto.TeamDto;
import com.qinfenfeng.roadmeets.service.TeamService;
import com.qinfenfeng.roadmeets.utils.common.JSONUtils;
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
     * @param teamDto
     * @return
     * @throws ReleaseTeamException
     * @throws NoUserException
     */
    @PostMapping("/releaseTeam")
    public boolean releaseTeam(HttpServletRequest request, @RequestBody TeamDto teamDto) throws ReleaseTeamException,
            NoUserException {
        String token = request.getHeader("token");
        return teamService.releaseTeam(token, teamDto);
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
}
