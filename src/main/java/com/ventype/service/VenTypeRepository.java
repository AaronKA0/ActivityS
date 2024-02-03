package com.ventype.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ventype.model.VenTypeVO;

public interface VenTypeRepository extends JpaRepository<VenTypeVO, Integer> {
	
	@Query(value = "from VenTypeVO where venTypeName =:name")
	VenTypeVO getByName(String name);

}
