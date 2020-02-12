package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.*;
import com.qinfenfeng.roadmeets.dto.vo.JoinTeamVo;
import com.qinfenfeng.roadmeets.mbg.model.Team;
import com.qinfenfeng.roadmeets.mbg.model.TeamUserRel;
import com.qinfenfeng.roadmeets.utils.exception.*;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * 配置事务
 * 隔离级别：可重复读
 * 事务传播：支持当前事务，如果当前没有事务，就新建一个事务。
 * 回滚条件：所有异常
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface TeamService {
    boolean releaseTeam(ReleaseTeamDto team) throws ReleaseTeamException, NoUserException;
    List<AdviceTeamDto> adviceTeam() throws NoUserException;
    JoinTeamVo joinTeam(JoinTeamDto joinTeamDto) throws NoTeamException;
    TeamInfoDto teamInfo(Long teamId);
    boolean agreeJoin(AgreeJoinDto agreeJoinDto) throws JoinUserException;
    Collection getJoinUserId(Long teamId);
    boolean endTeam(Long teamId) throws MonitorException;
    List<AdviceTeamDto> selectTeam(LocationDto locationDto) throws NoUserException;
    Set<TeamUserRel> teamHistory();
    Long joinState(Long teamId);
}
