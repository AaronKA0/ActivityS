package com.details.act.service.impl;

import com.details.act.dto.ActQueryParams;
import com.details.act.model.ActVOs;
import com.details.act.repository.ActRepositorys;
import com.details.act.service.IActServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class ActServices implements IActServices {

    @Autowired
    private ActRepositorys actRepository;

    @Override
    public Page<ActVOs> reviewActs(ActQueryParams actQueryParams, Pageable pageable) {

        if (actQueryParams.getActStatus() != null) {
            Byte actStatus2 = 2; //增加已取消活動的也搜出來

            return actRepository.findByMemIdAndActStatus(actQueryParams.getMemId(), actQueryParams.getActStatus(), actStatus2, pageable);
        }

        return actRepository.findByMemId(actQueryParams.getMemId(), pageable);
    }

    @Override
    public ActVOs deleteActReg(Integer actId) {
        Optional<ActVOs> actById = actRepository.findById(actId);

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

    @Override
    public List<ActVOs> getNewActs() {
        return actRepository.findByMemIdNotOrderByActCrTimeDesc(1, PageRequest.of(0, 4));
    }

    @Override
    public List<ActVOs> getOfficialActs() {
        return actRepository.findByMemIdOrderByActCrTimeDesc(1, PageRequest.of(0, 4));
    }
}
