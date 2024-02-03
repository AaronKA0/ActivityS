// https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

package com.emp.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmpRepository extends JpaRepository<EmpVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from employee where empId =?1", nativeQuery = true)
	void deleteByEmpId(Integer empId);

	EmpVO findByEmpAcc(String empAcc);
	
	Optional<EmpVO> findByEmpEmail(String empEmail);
}