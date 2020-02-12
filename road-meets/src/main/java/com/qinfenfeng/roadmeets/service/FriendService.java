package com.qinfenfeng.roadmeets.service;

import com.qinfenfeng.roadmeets.dto.EvaluateDto;
import com.qinfenfeng.roadmeets.dto.FriendDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface FriendService {
    boolean evaluate(EvaluateDto evaluateDto);
    List<FriendDto> getFriend();
}
