package com.ventype;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.ven.VenVO;

public interface VenTypeRepository extends JpaRepository<VenTypeVO, Integer> {
	
	@Query(value = "from VenTypeVO where venTypeName =:name")
	VenTypeVO getByName(String name);

}
