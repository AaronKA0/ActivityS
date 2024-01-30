package com.actreg.service.impl;

import com.details.act.model.ActVO;
import com.details.act.repository.ActRepository;
import com.actreg.dto.*;
import com.actreg.model.ActRegVO;
import com.actreg.repository.ActRegRepository;
import com.actreg.service.IActRegService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ActRegService implements IActRegService {

    @Autowired
    private ActRegRepository actRegRepository;

    @Autowired
    private ActRepository actRepository;

    @Autowired
    private ModelMapper modelMapper;

    //log紀錄
    private final static Logger log = LoggerFactory.getLogger(ActRegService.class);

    @Override
    public Page<ActRegVO> findByActId(Integer actId, Pageable pageable) {
        return actRegRepository.findByAct_ActId(actId, pageable);
    }

    @Override
    public Page<ActRegVO> getActRegs(Integer memId, ActRegQueryParams actRegQueryParams, Pageable pageable) {

        if (actRegQueryParams.getRegStatus() != null) {
            return actRegRepository.findByMemIdAndRegStatus(memId, actRegQueryParams.getRegStatus(), pageable);
        }

        if (actRegQueryParams.getActStatus() != null) {
            return actRegRepository.findRegByMemIdAndActStatus(memId, pageable);
        }

        return actRegRepository.findRegByMemId(memId, pageable);
    }

    @Override
    public ActRegVO getActReg(Integer actId, Integer memId) {
        return actRegRepository.findByAct_ActIdAndMemId(actId, memId);

//        return actRegRepository.findByActRegIdAndFetchActEagerly(actId).orElse(null);
    }

    @Override
    @Transactional
    public ActRegVO createActReg(ActRegRequest actRegRequest) {

        //報名前先查詢有沒有報名過
        ActRegVO byActActIdAndMemId = actRegRepository.findByAct_ActIdAndMemId(actRegRequest.getActId(), actRegRequest.getMemId());

        if (byActActIdAndMemId == null) { //找不到的話才可以報名 找到的話代表報名過了

            ActRegVO actReg = new ActRegVO();
            BeanUtils.copyProperties(actRegRequest, actReg);
            actReg.setRegTime(new Date());
            actReg.setAct(actRepository.findById(actRegRequest.getActId()).orElse(null));

            //如果報名人數超過活動人數上限 則不能報名
            ActVO act = actRepository.findById(actRegRequest.getActId()).orElse(null);
            if ((act.getActUpper() - act.getActCount()) - actRegRequest.getRegTotal() < 0) {
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
            }
            return actRegRepository.save(actReg);
        }

        return null;
    }

    @Override
    @Transactional
    public ActRegVO updateActReg(ActRegStatus actRegStatus) {

//        ActVO act = actRepository.findById(actRegStatus.getActId()).orElse(null);

        ActRegVO actReg = actRegRepository.findByAct_ActIdAndMemId(actRegStatus.getActId(), actRegStatus.getMemId());

        Integer actRegId = actReg.getActRegId(); //為了解決modelMapper映射問題 先將PK存起來 mapper完後再將PK set回正確的
        Byte actStatus = actReg.getAct().getActStatus();
        modelMapper.map(actRegStatus, actReg);
        actReg.setActRegId(actRegId); //modelMapper映射會將actId的值連動改到PK-actRegId
        actReg.getAct().setActStatus(actStatus);

        switch (actRegStatus.getRegStatus()) {
            case 2:
                //取消過現在再次報名
                log.info("再次報名");
                //如果報名人數超過活動人數上限 則不能報名
                if ((actReg.getAct().getActUpper() - actReg.getAct().getActCount()) - actRegStatus.getRegTotal() < 0) {
                    throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED);
                }
                break;
            case 4:
                //會員自己取消報名
                log.info("已取消報名");
                break;
        }
        return actRegRepository.save(actReg);
    }

    @Override
    @Transactional
    public ActRegVO reviewActReg(ActRegReviewRequest actRegReviewRequest) {

        ActRegVO actReg = actRegRepository.findById(actRegReviewRequest.getActRegId()).orElse(null);

        actReg.setRegStatus(actRegReviewRequest.getRegStatus());

        //審核報名者
        switch (actRegReviewRequest.getRegStatus()) {
            case 1:
                //失敗要通知會員
                log.info("通知會員報名失敗");
                break;
            case 3:
                //通過要將活動table參加人數增加
                actReg.getAct().setActCount(actReg.getAct().getActCount() + actReg.getRegTotal());
                log.info("通知會員報名成功");
                //通過通知會員
                break;
        }
        return actRegRepository.save(actReg);
    }

    @Override
    public List<MemNameAndPicDTO> findMemNameAndPic(Integer actId, Integer isActPart) {

        List<Object[]> membersAndPicByPart = actRegRepository.findMembersAndPicAndMemIdByPart(actId, isActPart);

        List<MemNameAndPicDTO> dtos = new ArrayList<>();

        for (Object[] obj : membersAndPicByPart) {
            MemNameAndPicDTO memNameAndPicDTO = new MemNameAndPicDTO();
            memNameAndPicDTO.setMemName((String) obj[0]);
            memNameAndPicDTO.setMemPic((byte[]) obj[1]);
            memNameAndPicDTO.setMemId((Integer) obj[2]);
            dtos.add(memNameAndPicDTO);
        }
        return dtos;
    }
}
