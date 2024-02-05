package com.venclosed.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.venclosed.model.VenClosedVO;

public interface VenClosedRepository extends JpaRepository<VenClosedVO, Integer>{

    
    @Query(value = "select * from venue_closed_date where ven_id =:venId", nativeQuery = true)
    List<VenClosedVO> getClosedbyVen(Integer venId);
}
