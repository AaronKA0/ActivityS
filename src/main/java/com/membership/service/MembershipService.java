package com.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.model.MembershipVO;

import java.util.List;
import java.util.Optional;

@Service("membershipService")
public class MembershipService {

	@Autowired
	MembershipRepository repository;

	public void addMembership(MembershipVO membershipVO) {
		repository.save(membershipVO);
	}

	public void updateMembership(MembershipVO membershipVO) {
		repository.save(membershipVO);
	}

	public void deleteSignUp(Integer memId) {
		if (repository.existsById(memId)) {
			repository.deleteById(memId);
		}
	}

	public MembershipVO getOneMembership(Integer memId) {
		Optional<MembershipVO> optional = repository.findById(memId);
		return optional.orElse(null);
	}

	public List<MembershipVO> getAll() {
		return repository.findAll();
	}

//	--------------------檢查帳號重複-------------------
	public MembershipVO findByMemAcc(String memAcc) {
		MembershipVO membership = repository.findByMemAcc(memAcc);
		return membership;
	}

//	--------------------檢查信箱重複-------------------
	public MembershipVO findByMemEmail(String memEmail) {
		MembershipVO membership = repository.findByMemEmail(memEmail);
		return membership;
	}

//	-----------------------登入-----------------------
	public boolean login(String memAcc, String memPwd) {
		MembershipVO membership = repository.findByMemAcc1(memAcc);
		if (membership != null && membership.getMemPwd().equals(memPwd)) {
			return true;
		}
		return false;
	}

	public boolean memberExists(String memAcc) {
		return false;
	}

//	-----------------------狀態更改-----------------------
	public MembershipVO getStatusMembership(Integer memId) {
		Optional<MembershipVO> optional = repository.findById(memId);
		return optional.orElse(null);
	}

}
