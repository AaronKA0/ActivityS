package com.membership.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.membership.model.MembershipVO;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service("membershipService")
public class MembershipService {

	@Autowired
	MembershipRepository repository;

	@Autowired
	MailService MailSvc;

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

//	-----------------------MD5加密--------------------
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

			// 將更新後的會員信息保存回資料庫
			repository.save(membershipVO);
		} else {
			// memAcc 不存在，可能需要採取相應的錯誤處理
		}

	}

//	------------從MembershipVO中獲取會員姓名-----------------
	public String getMemName(String memAcc) {
		MembershipVO membership = repository.getMemName(memAcc);

		// 從MembershipVO中獲取會員編號
		String memName = membership.getMemName();

		return memName;
	}

//	-----------------會員每次登入時更新上線時間------------------
	public void updateMemLoginTime(String memAcc) {
		MembershipVO membership = repository.findByMemAcc(memAcc);

		if (membership != null) {
			// 更新登入時間
			membership.setMemLoginTime(Timestamp.valueOf(LocalDateTime.now()));
			repository.save(membership);
		}
	}

//  --------------取的會員的帳戶狀態(啟用2/停用1)-----------------
	public int getIsAccEna(String memAcc) {
		MembershipVO membership = repository.getIsAccEna(memAcc);

		int IsAccEna = membership.getIsAccEna();

		return IsAccEna;
	}

//  ----------------------取得會員編號-------------------------
	public String getMemberEmailById(String memId) {
		// 從memId 從資料庫取得會員的信箱
		MembershipVO membership = repository.findByMemEmail(memId);

		if (memId != null) {
			return membership.getMemEmail(); // 取得會員信箱
		} else {
			return null;
		}
	}

//  ------------------新增發送封鎖通知郵件的方法--------------------
	public void sendAccountBlockedEmail(String to) {
		MailSvc.sendAccountBlockedEmail(to, to);
	}

//  ------------------------取會員信箱-------------------------
	public String getMemberEmailById(Integer memId) {

		MembershipVO membership = repository.getMemEmail(memId);

		// 檢查 MembershipVO 是否不為空
		if (membership != null) {
			// 返回會員信箱
			return membership.getMemEmail();
		} else {
			throw new RuntimeException("找不到 ID 為 " + memId + " 的會員");
		}
	}



}