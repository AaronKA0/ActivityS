package com.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.dept.model.DeptService;
import com.dept.model.DeptVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;


//每個controller都會添加設定的東西到model內

@ControllerAdvice
public class AllSecurityController {

	@Autowired
	private EmpService empSvc;

	@Autowired
	private DeptService deptSvc;

	@ModelAttribute
	public void addAttributes(Model model, Authentication authentication) {
		if (authentication != null && authentication.isAuthenticated()) {
			// 獲取當前用戶的用戶名
			String username = authentication.getName();
			// 使用服務來查找完整的EmpVO，包括部門信息
			EmpVO empVO = empSvc.findByEmpAcc(username);
			if (empVO != null) {
				// 添加empName和dname到模型
			    model.addAttribute("empId", empVO.getEmpId());
				model.addAttribute("empName", empVO.getEmpName());
				DeptVO deptVO = empVO.getDeptVO();
				if (deptVO != null) {
					model.addAttribute("dname", deptVO.getDname());
					model.addAttribute("deptno", deptVO.getDeptno());
				}
			}
		}
	}
}
