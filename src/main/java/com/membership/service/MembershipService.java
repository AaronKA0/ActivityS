package com.membership.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.membership.model.MembershipVO;
import com.venue.model.VenVO;

import java.sql.Date;
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

//	    ------------------MD5加密-------------------
		String hashedPassword = DigestUtils.md5DigestAsHex(memPwd.getBytes());

//		System.out.println("MemPwd" + MemPwd);

		if (membership != null && membership.getMemPwd().equals(hashedPassword)) {

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

//	------------從MembershipVO中獲取會員編號-----------------
	public int getMemId(String memAcc) {
		MembershipVO membership = repository.getMemId(memAcc);

		// 從MembershipVO中獲取會員編號
		int memId = membership.getMemId();

		return memId;
	}

//	------------從redis的會員信箱去取得該會員的會員帳號------------	
	public String getMemAccByEmail(String memEmail) {

		MembershipVO membershipVO = repository.findByMemEmail(memEmail);
		return (membershipVO != null) ? membershipVO.getMemAcc() : null;
	}

//	---------從redis取得的密碼在登入驗證碼時將密碼寫進資料庫----------
	public void updateMembership(String memAcc, String newPassword) {

		MembershipVO membershipVO = repository.findByMemAcc(memAcc);
		if (membershipVO != null) {
			
			// 使用新密碼更新會員的密碼
			membershipVO.setMemPwd(newPassword);

			// 將更新後的會員信息保存回數據
			repository.save(membershipVO);
		} else {
			// memAcc 不存在，可能需要採取相應的錯誤處理
			// 例如，拋出一個自定義的異常或者記錄錯誤信息
		}

	}

//	------------從MembershipVO中獲取會員姓名-----------------
	public String getMemName(String memAcc) {
		MembershipVO membership = repository.getMemName(memAcc);

		// 從MembershipVO中獲取會員編號
		String memName = membership.getMemName();

		return memName;
	}	
	
	
	
	// Aaron
//  ------------從MembershipVO中獲取會員基本資料-----------------
	public MembershipVO getMemInfo(Integer memId){
        return repository.getMemInfo(memId);
    }

}
