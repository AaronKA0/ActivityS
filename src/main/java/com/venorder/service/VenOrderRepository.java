package com.venorder.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.venorder.model.VenOrderVO;
import com.venue.model.VenVO;

public interface VenOrderRepository extends JpaRepository<VenOrderVO, Integer>{

    @Query(value = "from VenOrderVO where orderDate =:orderDate")
    List<VenOrderVO> getByOrderDate(Date orderDate);
    
    @Query(value = "from VenOrderVO where venVO =:venVO")
    List<VenOrderVO> getVenCom(VenVO venVO);
    
    @Query(value = "from VenOrderVO where orderDate <:orderDate")
    List<VenOrderVO> getLessDay(Date orderDate);
    
    
    // Nathan
    @Query(value = "from VenOrderVO where memVO.memId =:memId")
    List<VenOrderVO> getMemOrders(Integer memId);
    

}