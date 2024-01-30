package com.details.act.service;

import com.details.act.dto.ActQueryParams;
import com.details.act.model.ActVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IActService {

    Page<ActVO> reviewActs(ActQueryParams actQueryParams, Pageable pageable);

    ActVO deleteActReg(Integer actId);
}
