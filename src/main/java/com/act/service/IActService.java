package com.act.service;

import com.act.dto.ActVoRequest;
import com.act.model.ActVO;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IActService {
    ActVO createAct(ActVoRequest actVoRequest);
    ActVO updateAct(Integer id, ActVoRequest actVoRequest);

    List<ActVO> getAllActs();

    ActVO getActById(Integer id);
    
    
    
    
 // ++++++++++++++ Aaron ++++++++++++++
    @Query(value = "select* from activity where act_start_time > :start and act_start_time < :end", nativeQuery = true)
    List<ActVO> getByStart(@Param("start") Date start, @Param("end") Date end);   

  
}
