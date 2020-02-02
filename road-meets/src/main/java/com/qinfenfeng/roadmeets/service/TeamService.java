package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.TeamDto;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.utils.exception.NoUserException;
import com.qinfenfeng.roadmeets.utils.exception.ReleaseTeamException;

import java.util.List;

public interface TeamService {
    boolean releaseTeam(String token, TeamDto team) throws ReleaseTeamException, NoUserException;
    List<Team> adviceTeam() throws NoUserException;
}
