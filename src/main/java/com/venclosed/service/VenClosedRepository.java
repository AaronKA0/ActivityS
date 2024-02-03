package com.venclosed.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.venclosed.model.VenClosedVO;

public interface VenClosedRepository extends JpaRepository<VenClosedVO, Integer>{

    
    @Query(value = "from VenClosedVO where venVO.venId =:venId")
    List<VenClosedVO> getClosedbyVen(Integer venId);
}
