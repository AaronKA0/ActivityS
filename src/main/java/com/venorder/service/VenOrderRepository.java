package com.venorder.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.notify.model.NotifyVO;
import com.venorder.model.VenOrderVO;
import com.venue.model.VenVO;

public interface VenOrderRepository extends JpaRepository<VenOrderVO, Integer>{

    @Query(value = "from VenOrderVO order by orderDate desc")
    List<VenOrderVO> findAll();
    
    @Query(value = "from VenOrderVO where orderDate =:orderDate")
    List<VenOrderVO> getByOrderDate(Date orderDate);
    
    @Query(value = "from VenOrderVO where venVO.venId =:venId")
    List<VenOrderVO> getVenCom(Integer venId);
    
    @Query(value = "from VenOrderVO where orderDate <:orderDate")
    List<VenOrderVO> getLessDay(Date orderDate);
    
}