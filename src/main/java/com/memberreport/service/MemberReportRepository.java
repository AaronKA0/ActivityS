package com.memberreport.service;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.memberreport.model.MemberReportVO;

public interface MemberReportRepository extends JpaRepository<MemberReportVO, Integer>{

	@Transactional
	@Modifying
	@Query(value = "delete from memberreport where repId =?1", nativeQuery = true)
	void deleteByRepId(int repId);
	
	
	
	@Query(value = "SELECT * from member_report where reporter_id =:reporterId and reportee_id =:reporteeId and rep_status =1", nativeQuery = true)
	MemberReportVO getReport(int reporterId, int reporteeId);
}

