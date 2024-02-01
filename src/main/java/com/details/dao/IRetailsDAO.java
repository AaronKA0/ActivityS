package com.details.dao;

import com.details.dto.ActDTO;
import com.details.dto.ActRandomDTO;

import java.util.List;

public interface IRetailsDAO {

    ActDTO getDetail(Integer actId);

    List<ActRandomDTO> randomFourAct(Integer actTypeId, Integer actId);
}
