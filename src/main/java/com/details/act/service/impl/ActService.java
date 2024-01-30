package com.details.act.service.impl;

import com.details.act.dto.ActQueryParams;
import com.details.act.model.ActVO;
import com.details.act.repository.ActRepository;
import com.details.act.service.IActService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class ActService implements IActService {

    @Autowired
    private ActRepository actRepository;

    @Override
    public Page<ActVO> reviewActs(ActQueryParams actQueryParams, Pageable pageable) {

        if (actQueryParams.getActStatus() != null) {
            return actRepository.findByMemIdAndActStatus(actQueryParams.getMemId(), actQueryParams.getActStatus(), pageable);
        }

        return actRepository.findByMemId(actQueryParams.getMemId(), pageable);
    }

    @Override
    public ActVO deleteActReg(Integer actId) {
        Optional<ActVO> actById = actRepository.findById(actId);

        if (actById.isPresent()) {
            if (actById.get().getActStatus() != 4) { //4 = 報名中的才能取消
                return null;
            }
            actById.get().setActStatus((byte) 2);//2 = 取消活動
            return actRepository.save(actById.get());

        } else {
            return null;
        }
    }
}
