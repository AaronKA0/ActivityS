package com.actfollowed.service.impl;

import com.details.act.model.ActVOs;
import com.details.act.repository.ActRepositorys;
import com.actfollowed.dto.ActFollowRequest;
import com.actfollowed.model.ActFollowedVO;
import com.actfollowed.repository.ActFollowedRepository;
import com.actfollowed.service.IActFollowedService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ActFollowedService implements IActFollowedService {

    @Autowired
    private ActFollowedRepository actFollowedRepository;
    @Autowired
    private ActRepositorys actRepository;

    @Override
    public Page<ActFollowedVO> getActFollows(Integer memId, Pageable pageable) {
        Byte folStatus = 2; // 2 = 關注的活動

        Page<ActFollowedVO> actFollowed = actFollowedRepository.findByMemIdAndFolStatus(memId, folStatus, pageable);
//        ActVO act = actRepository.findById(actId).orElse(null);

//        ActFollowDTO actFollowDTO = new ActFollowDTO();
//        actFollowDTO.setActFollowed(actFollowed);
//        actFollowDTO.setAct(act);

        return actFollowed;
    }

    @Override
    public Byte getActFollows(Integer actId, Integer memId) {
        ActFollowedVO actFollowed = actFollowedRepository.findByActIdAndMemId(actId, memId).orElse(null);
        if (actFollowed != null) {
            return actFollowed.getFolStatus();
        } else {
            return null;
        }
    }

    @Override
    public ActFollowedVO createActFollow(ActFollowRequest actFollowRequest) {

        ActFollowedVO actFollowed = new ActFollowedVO();
        BeanUtils.copyProperties(actFollowRequest, actFollowed);

        ActVOs act = actRepository.findById(actFollowRequest.getActId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //將活動table的關注人數欄位增加
        act.setActFollowCount(act.getActFollowCount() + 1);

        return actFollowedRepository.save(actFollowed);
    }

    @Override
    public ActFollowedVO updateActFollow(ActFollowRequest actFollowRequest) {

        ActFollowedVO actFollowed = new ActFollowedVO();
        BeanUtils.copyProperties(actFollowRequest, actFollowed);

        ActVOs act = actRepository.findById(actFollowRequest.getActId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        //將活動table的關注人數欄位減去
        act.setActFollowCount(act.getActFollowCount() - 1);

        return actFollowedRepository.save(actFollowed);
    }


}
