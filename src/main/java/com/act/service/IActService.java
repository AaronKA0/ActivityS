package com.act.service;

import com.act.dto.ActVoRequest;
import com.act.model.ActVO;

import java.util.List;

public interface IActService {
    ActVO createAct(ActVoRequest actVoRequest);
    ActVO updateAct(Integer id, ActVoRequest actVoRequest);

    List<ActVO> getAllActs();

    ActVO getActById(Integer id);

  
}
