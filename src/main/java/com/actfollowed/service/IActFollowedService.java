package com.actfollowed.service;

import com.actfollowed.dto.ActFollowRequest;
import com.actfollowed.model.ActFollowedVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActFollowedService {

    Page<ActFollowedVO> getActFollows(Integer memId,  Pageable pageable);

    Byte getActFollows(Integer actId,  Integer memId);

    ActFollowedVO createActFollow(ActFollowRequest actFollowRequest);

    ActFollowedVO updateActFollow(ActFollowRequest actFollowRequest);
}
