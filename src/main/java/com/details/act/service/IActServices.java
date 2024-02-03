package com.details.act.service;

import com.details.act.dto.ActQueryParams;
import com.details.act.model.ActVOs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IActServices {

    Page<ActVOs> reviewActs(ActQueryParams actQueryParams, Pageable pageable);

    ActVOs deleteActReg(Integer actId);

    List<ActVOs> getNewActs();

    List<ActVOs> getOfficialActs();
}
