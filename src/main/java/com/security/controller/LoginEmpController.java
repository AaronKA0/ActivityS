package com.security.controller;

import java.security.SecureRandom;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.emp.model.EmpRepository;
import com.emp.model.EmpVO;
import com.security.service.EmpMailService;

@Controller
public class LoginEmpController {

	@Autowired
	private EmpRepository empRepository;

	@Autowired
	private EmpMailService mailService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	// 登入
	@GetMapping("/login")
	public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
			HttpServletRequest request, Model model) {

		// 判斷如果是登出，清除session
		if ("true".equals(logout)) {
			request.getSession().invalidate();
			model.addAttribute("message", "您已成功登出。");
		}

		// 檢查是否有錯誤訊息
		if (error != null) {
			// 根據錯誤類型顯示不同的錯誤訊息
			String errorMessage = "用戶名稱或密碼錯誤";

			model.addAttribute("errorMessage", errorMessage);
		}

		return "back-end/login";
	}

	@PostMapping("/forgotPassword")
	public String forgotPassword(@RequestParam("empAcc") String empAcc, @RequestParam("empEmail") String empEmail,
			Model model) {
		// 檢查用戶是否存在
		EmpVO empVO = empRepository.findByEmpAcc(empAcc);
		System.out.println("3");
		if (empVO == null) {
			model.addAttribute("error", "找不到該電子郵件地址的用戶。");
			System.out.println("1");
			return "redirect:login";
			
		}

		// 檢查信箱和帳號是否匹配
		if (!(empVO.getEmpEmail().equals(empEmail))) {
			model.addAttribute("error", "提供的email和帳號不匹配");
			System.out.println("2");
			return "redirect:login";
		}

		// 生成隨機密碼
		String newPassword = generateRandomPassword();
		newPassword = "pass001";
		
		// 發送郵件
		EmpMailService mailService = new EmpMailService();
		String subject = "忘記密碼重製";
		String messageText = "您的新密碼是: " + newPassword;
		mailService.sendMail(empEmail, subject, messageText);

		// 更新數據庫中的密碼
		String encodedPassword = passwordEncoder.encode(newPassword);
		empVO.setEmpPwd(encodedPassword);
		empRepository.save(empVO);

		model.addAttribute("message", "新密碼已發送到您的電子郵件。");

		return "redirect:login";
	}

	// 生成隨機密碼的方法
	private String generateRandomPassword() {
		int length = 10;
		// 定義字符集
		String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder password = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = new SecureRandom().nextInt(characterSet.length());
			password.append(characterSet.charAt(randomIndex));
		}
		return password.toString();
	}

}
