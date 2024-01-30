package com.actreg.service;

import com.actreg.dto.*;
import com.actreg.model.ActRegVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IActRegService {

    Page<ActRegVO> findByActId(Integer actId, Pageable pageable);

    Page<ActRegVO> getActRegs(Integer memId, ActRegQueryParams actRegQueryParams, Pageable pageable);

    ActRegVO getActReg(Integer actId, Integer memId);

    ActRegVO createActReg(ActRegRequest actRegRequest);

    ActRegVO reviewActReg(ActRegReviewRequest actRegReviewRequest);

    ActRegVO updateActReg(ActRegStatus actRegStatus);


    List<MemNameAndPicDTO> findMemNameAndPic(Integer actId, Integer isActPart);
}
