package com.postreport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memberreport.model.MemberReportVO;
import com.postreport.model.PostReportVO;

@Service("postReportService")
public class PostReportService {

	@Autowired
	PostReportRepository repository;

	public void addPostReport(PostReportVO postReportVO) {
		repository.save(postReportVO);
	}

	public void updatePostReport(PostReportVO postReportVO) {
		repository.save(postReportVO);
	}

	public void deletePostReport(Integer repId) {
		if (repository.existsById(repId))
			repository.deleteByRepId(repId);
	}

	public PostReportVO getOnePostReport(Integer repId) {
		Optional<PostReportVO> optional = repository.findById(repId);
//		return optional.get();
		return optional.orElse(null);

	}

	public List<PostReportVO> getAll() {
		return repository.findAll();
	}

//	-----------------------狀態更改-----------------------
	public PostReportVO getStatusPostReport(Integer repId) {
		Optional<PostReportVO> optional = repository.findById(repId);
		return optional.orElse(null);
	}
	
	public List<PostReportVO> getReport(Integer memIdA, Integer memIdB) {
		return repository.getReport(memIdA, memIdB);
	}
}
