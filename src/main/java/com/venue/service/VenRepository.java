package com.venue.service;

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
	VenVO findByName(String name);

}