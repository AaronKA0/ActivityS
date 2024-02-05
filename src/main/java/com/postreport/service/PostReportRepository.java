package com.postreport.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.postreport.model.PostReportVO;

public interface PostReportRepository extends JpaRepository<PostReportVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from postreport where repId =?1", nativeQuery = true)
	void deleteByRepId(int repId);

	
	@Query(value = "SELECT * from post_report where mem_id =:reporterId and reportee_id =:reporteeId and rep_status =1", nativeQuery = true)
	List<PostReportVO> getReport(int reporterId, int reporteeId);
}
