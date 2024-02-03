package com.memberreport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memberreport.model.MemberReportVO;

@Service("memberReportService")
public class MemberReportService {

	@Autowired
	MemberReportRepository repository;

	public void addMemberReport(MemberReportVO memberReportVO) {
		repository.save(memberReportVO);
	}

	public void updateMemberReport(MemberReportVO memberReportVO) {
		repository.save(memberReportVO);
	}

	public void deleteMemberReport(Integer repId) {
		if (repository.existsById(repId))
			repository.deleteByRepId(repId);
//		repository.deleteByRepId(repId);
	}

	public MemberReportVO getOneMemberReport(Integer repId) {
		Optional<MemberReportVO> optional = repository.findById(repId);
//		return optional.get();
		return optional.orElse(null);

	}

	public List<MemberReportVO> getAll() {
		return repository.findAll();
	}

	
//	-----------------------狀態更改-----------------------
	public MemberReportVO getStatusMemberReport(Integer repId) {
		Optional<MemberReportVO> optional = repository.findById(repId);
		return optional.orElse(null);
	}

}
