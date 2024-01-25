package com.venue.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.venue.model.VenVO;

public interface VenRepository extends JpaRepository<VenVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from venue where ven_id =?1", nativeQuery = true)
	void deleteByVenId(int venId);
	
	@Query(value = "from VenVO where venName =:name")
	VenVO getByName(String name);	
	
	@Query(value = "SELECT * FROM venue v WHERE v.ven_id NOT IN "
	                    + "(SELECT o.ven_id FROM venue_order o WHERE o.order_date =:orderDate)"
	                + " AND v.ven_id NOT IN "
	                    + "(SELECT c.ven_id FROM venue_closed_date c WHERE c.closed_date =:orderDate)"
	                + "ORDER BY v.ven_id", nativeQuery = true)
    List<VenVO> pickByOrderDate(Date orderDate);

}
