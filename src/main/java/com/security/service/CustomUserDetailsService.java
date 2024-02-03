package com.security.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.emp.model.EmpVO;
import com.emp.model.EmpRepository; 
import com.emp.model.EmpService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final EmpRepository empRepository;
	private final EmpService empService;

	@Autowired
	public CustomUserDetailsService(EmpRepository empRepository, PasswordEncoder passwordEncoder,EmpService empService) {
		this.empRepository = empRepository;
		this.empService = empService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		EmpVO empVO = empRepository.findByEmpAcc(username);
		if (empVO == null) {
			throw new UsernameNotFoundException("用戶名稱或密碼錯誤");
		}
		
		 // 檢查用戶狀態
	    if (empVO.getEmpStatus() == 0) {
	        throw new UsernameNotFoundException("用戶已離職");
	    }
		
		//更新最後登錄時間
		empService.updateEmp(empVO);
	
		
		// 返回資料庫中加密過的密碼
		return new User(empVO.getEmpAcc(), empVO.getEmpPwd(), new ArrayList<>());
	}
}