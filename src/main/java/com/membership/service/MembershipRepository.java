package com.membership.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.membership.model.MembershipVO;

public interface MembershipRepository extends JpaRepository<MembershipVO, Integer> {

	@Transactional
	@Modifying
	@Query(value = "delete from membership where memId =?1", nativeQuery = true)
	void deleteByMemId(int memId);

//	--------------------檢查帳號重複-------------------
	@Query(value = "from MembershipVO where memAcc =:memAcc")
	MembershipVO findByMemAcc(String memAcc);

//	--------------------檢查信箱重複-------------------
	@Query(value = "from MembershipVO where memEmail =:memEmail")
	MembershipVO findByMemEmail(String memEmail);

	@Query(value = "from MembershipVO where memAcc =:memAcc")
//	---------------------登入------------------------
	MembershipVO findByMemAcc1(String memAcc);

//	------------從MembershipVO中獲取會員編號-----------------
	@Query(value = "from MembershipVO where memAcc =:memAcc")
	MembershipVO getMemId(String memAcc);

	
//	------------從MembershipVO中獲取會員姓名-----------------
	@Query(value = "from MembershipVO where memAcc =:memAcc")
	MembershipVO getMemName(String memAcc);

	
//	------------從MembershipVO中獲取會員帳戶狀態-----------------
	@Query(value = "from MembershipVO where memAcc =:memAcc")	
	MembershipVO getIsAccEna(String memAcc);

//  ------------從MembershipVO中獲取會員信箱-----------------
	@Query(value = "from MembershipVO where memId =:memId")	
	MembershipVO getMemEmail(Integer memId);

	

	
	


	
}
