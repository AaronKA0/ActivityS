package com.membership.controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.membership.model.MembershipVO;
import com.membership.service.MailService;
import com.membership.service.MembershipService;


import com.membership.service.RedisService;

@Controller
@RequestMapping("/membership")
public class LoginController {
	
	@GetMapping("ZuoHuo")
	public String ZuoHuo(ModelMap model) {
		return "front-end/zuo-huo";
	}

	
	@Autowired
	MembershipService membershipSvc;

	@Autowired
	MailService mailSvc;

	@Autowired
	RedisService redisSvc;

	// ---------------------------登入---------------------------
	@PostMapping("login")
	public String login(@RequestParam("memAcc") String memAcc, @RequestParam("memPwd") String memPwd, Model model,
			HttpServletRequest request, HttpSession session) throws IOException {

		if (membershipSvc.login(memAcc, memPwd)) {
			// 登入成功
			int memId = membershipSvc.getMemId(memAcc); // 使用新的方法獲取會員編號
			String memName = membershipSvc.getMemName(memAcc);
			int IsAccEna = membershipSvc.getIsAccEna(memAcc);

			// 帳戶狀態 == 1(停用)，就無法登入會員
			if (IsAccEna == 1) {
				// 帳戶被停用
				model.addAttribute("errorMessage", "您的帳號已被停用，不能登入");
				return "front-end/membership/login";
			}

			request.getSession().setAttribute("memAcc", memAcc);
			request.getSession().setAttribute("memId", memId); // 將會員編號存入 session

			request.getSession().setAttribute("memName", memName); // 將會員姓名存入 session
			request.getSession().setAttribute("IsAccEna", IsAccEna); // 將會員帳戶狀態存入 session

			Integer a = (Integer) session.getAttribute("memId");
			String b = (String) session.getAttribute("memName");
			Integer c = (Integer) session.getAttribute("IsAccEna");

			System.out.println(a);
			System.out.println(b);
			System.out.println(c);
//	        System.out.print("line 65 :" + "memId:" + memId +" "+"memAcc:" + memAcc +" " + "memPwd:" + memPwd);


			// 每次登入就更新一次登入時間
			membershipSvc.updateMemLoginTime(memAcc);


			return "redirect:/Zuo-Huo";
//			return "redirect:/member";


		} else {

			// 登入失敗
			if (!membershipSvc.memberExists(memAcc)) {
				model.addAttribute("errorMessage", "帳號密碼有錯誤，請重新輸入");
			} else {
				model.addAttribute("errorMessage", "該會員不存在");
			}

			System.out.println(model.getAttribute("errorMessage"));
			// return "redirect:/membership/login";
			return "front-end/membership/login";
		}
	}

	// ---------------------------登出---------------------------
	@GetMapping("/logout")
	public String logout(HttpServletRequest request) {
		// 獲取 Session
		HttpSession session = request.getSession(false);

		// 如果Session存在，則進行登出操作
		if (session != null) {
			// 使Session失效
			session.invalidate();
		}

		// 重導向到登入頁面
		return "redirect:/membership/login";
	}

	// -------------------------忘記密碼--------------------------
	@PostMapping("forgetpassword")
	public String forgetpassword(@RequestParam("memEmail") String memEmail, Model model) {
		if (!(memEmail == null || memEmail.isEmpty())) {
			// 假設這是根據 memEmail 從數據庫中獲取會員 memAcc 的邏輯
			String memAcc = membershipSvc.getMemAccByEmail(memEmail);

			if (memAcc != null) {
				// 生成驗證碼
				String verificationCode = generateRandomVerificationCode();

				// 將驗證碼verificationCode 和 memAcc 保存到 Redis，過期時間為一小時為3600L
				redisSvc.saveToRedis(memAcc, verificationCode, 1800L);

				// 發送包含驗證連結的郵件，傳遞驗證碼作為參數
				mailSvc.sendVerificationCode(memEmail, "做伙Zuò huǒ密碼變更通知信!", verificationCode);

				model.addAttribute("success", "提交成功，請去信箱收信!");
				// model.addAttribute("emailSent",true);

				return "front-end/membership/forgetpassword";
			} else {
				model.addAttribute("error", "無此會員帳號");
				return "front-end/membership/forgetpassword";
			}
		} else {
			model.addAttribute("error", "請輸入信箱");
			return "front-end/membership/forgetpassword";
		}
	}

	private String generateRandomVerificationCode() {
		// 生成包含英數字的8位密碼
		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder verificationCode = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(characters.length());
			verificationCode.append(characters.charAt(index));
		}

		return verificationCode.toString();
	}

	// ---------------使用redis的密碼去做會員登入---->密碼更改為驗證碼-----------------

	@PostMapping("/updatepassword")
	public String updatepassword(@RequestParam("memAcc") String memAcc, @RequestParam("password") String newPassword,
			Model model) {

		String pwd = redisSvc.getFromRedis(memAcc);

		if (pwd == null || !pwd.equals(newPassword)) {

			model.addAttribute("error", "帳號及驗證碼有誤，請再次輸入。");

			return "front-end/membership/updatepassword";

		}

		// MD5加密
		String hashedPassword = DigestUtils.md5DigestAsHex(newPassword.getBytes());

		membershipSvc.updateMembership(memAcc, hashedPassword);
		redisSvc.removeFromRedis(memAcc + "_token");

		model.addAttribute("success", "密碼已成功更新，請使用新密碼登入。");

		return "front-end/membership/updatepassword";

	}

}
