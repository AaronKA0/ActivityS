package com.emp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.model.HibernateUtil_CompositeQuery_Emp3;

@Service("empService")
public class EmpService {

	@Autowired
	private EmpRepository repository;

	@Autowired
	private SessionFactory sessionFactory;

//	/*************************** 啟動時調用加密方法(com.pwd)取得資料庫未加密密碼進行加密 ************************/
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	public void encryptAllPasswords() {
//		List<EmpVO> employees = repository.findAll();
//		for (EmpVO emp : employees) {
//			String rawPassword = emp.getEmpPwd();
//			String encodedPassword = passwordEncoder.encode(rawPassword);
//			emp.setEmpPwd(encodedPassword);
//			repository.save(emp);
//		}
//	}

	public void addEmp(EmpVO empVO) {
		empVO.setEmpLoginTime(LocalDateTime.now()); // 設置當前時間為最後登錄時間
		repository.save(empVO);
	}

	public void updateEmp(EmpVO empVO) {
		empVO.setEmpLoginTime(LocalDateTime.now());
		repository.save(empVO);
	}

	public void deleteEmp(Integer empId) {
		if (repository.existsById(empId))
			repository.deleteByEmpId(empId);
//		    repository.deleteById(empId);
	}

	public EmpVO getOneEmp(Integer empId) {
		Optional<EmpVO> optional = repository.findById(empId);
//		return optional.get();
		return optional.orElse(null); // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<EmpVO> getAll() {
		return repository.findAll();
	}

	public List<EmpVO> getAll(Map<String, String[]> map) {
		return HibernateUtil_CompositeQuery_Emp3.getAllC(map, sessionFactory.openSession());
	}

	public EmpVO findByEmpAcc(String empAcc) {
		return repository.findByEmpAcc(empAcc);
	}

}