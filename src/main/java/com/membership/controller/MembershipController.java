package com.membership.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.membership.service.RedisService;
import com.notify.service.NotifyNow;

@Controller
@RequestMapping("/membership")
public class MembershipController {

	@Autowired
	MembershipService membershipSvc;

	@Autowired
	RedisService redisSvc;

	Gson gson = new Gson(); // gson
	

	@GetMapping("addMembership")
	public String addMembership(ModelMap model) {
		MembershipVO membershipVO = new MembershipVO();
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/addMembership";
	}

	@GetMapping("login")
	public String login(ModelMap model) {
		MembershipVO membershipVO = new MembershipVO();
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/login";
	}

	@GetMapping("forgetpassword")
	public String forgetpassword(ModelMap model) {
		MembershipVO membershipVO = new MembershipVO();
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/forgetpassword";
	}

	@GetMapping("updatepassword")
	public String updatepassword(ModelMap model) {
		MembershipVO membershipVO = new MembershipVO();
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/updatepassword";

	}

	@GetMapping("update_membership_input")
	public String update_membership_input(ModelMap model) {
		MembershipVO membershipVO = new MembershipVO();
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/update_membership_input";

	}

//	    --------------------檢查帳號重複-------------------
	@PostMapping("checkMemAcc")
	public @ResponseBody MembershipVO checkMemAcc(@RequestBody String json) {

//		====================帳號重複驗證======================
//		ObjectMapper mapper = new ObjectMapper();
//		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
//		};
//		Map<String, Object> data = new HashMap<String, Object>();
//		try {
//			data = mapper.readValue(json, typeRef);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}     															// 以上json也是相同的結果，可以參考

		MembershipVO member = gson.fromJson(json, MembershipVO.class); // 把json字串轉換成我需要的類別 字串--->物件
																		// 若MembershipVO物件傳換成json，可以使用tojson 物件--->字串
		return membershipSvc.findByMemAcc(member.getMemAcc()); // 從VO去取得我的(memAcc)
	}
//	   ===================================================

//	    --------------------檢查信箱重複-------------------
	@PostMapping("checkMemEmail")
	public @ResponseBody MembershipVO checkMemEmail(@RequestBody String json) {

//		====================信箱重複驗證======================
//		ObjectMapper mapper = new ObjectMapper();
//		TypeReference<Map<String, Object>> typeRef = new TypeReference<Map<String, Object>>() {
//		};
//		Map<String, Object> data = new HashMap<String, Object>();
//		try {
//			data = mapper.readValue(json, typeRef);
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
//
//		String memEmail = (String) data.get("memEmail");

		MembershipVO member = gson.fromJson(json, MembershipVO.class);

		return membershipSvc.findByMemEmail(member.getMemEmail()); // 將memberVO轉成json字串

	}
//	   ===================================================

//    --------------------insert-------------------
	@PostMapping("insert")
	public String insert(@Valid MembershipVO membershipVO, BindingResult result, ModelMap model,
			@RequestParam("memPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(membershipVO, result, "memPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				membershipVO.setMemPic(buf);
			}
		}
//		if (result.hasErrors() || parts[0].isEmpty()) {
//			return "back-end/signup/addSignUp";  //若無修改需要保留原本圖片所以註解起來
//		}

//	    ------------------MD5加密-------------------
		String hashedPassword = DigestUtils.md5DigestAsHex(membershipVO.getMemPwd().getBytes());
		membershipVO.setMemPwd(hashedPassword);

		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		membershipSvc.addMembership(membershipVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list);
		model.addAttribute("success", "- (新增成功)");
//		return "redirect:/membership/listAllMembership"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
		return "redirect:/membership/logging";
	}

//  ----------------getOne_For_Update-----------------

	@GetMapping("getOne_For_Update")
	public String getOne_For_Update(ModelMap model, HttpSession session) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		
		/*************************** 2.開始查詢資料 *****************************************/
		
		Integer memId = (Integer) session.getAttribute("memId"); 
		
		// EmpService empSvc = new EmpService();

		MembershipVO membershipVO = membershipSvc.getOneMembership(memId);

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/update_membership_input"; // 查詢完成後轉交update_emp_input.html
//		return "back-end/membership/select_page";
	}

//  ----------------getOne_For_Status(查會員單一帳戶權限的狀態)-----------------
	@PostMapping("getOne_For_Status")
	public String status_For_Update(@RequestParam("memId") String memId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		MembershipVO membershipVO = membershipSvc.getStatusMembership(Integer.valueOf(memId));
		System.out.println("membershipVO" + membershipVO);
		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list); // for select_page.html 第97 109行用

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("membershipVO", membershipVO);
		model.addAttribute("getOne_For_Status", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
//		return "back-end/membership/update_membership_input"; // 查詢完成後轉交update_emp_input.html
//		return "back-end/membership/select_page";
		return "back-end/membership/listAllMembership";
	}

////  ----------------update_Status(修改單一會員權限狀態)-----------------
//	@PostMapping("update_Status")
//	public String update_Status(@Valid MembershipVO membershipVO,ModelMap model){
//
//		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//		System.out.println("line 219: membershipVO" + membershipVO);
//		/*************************** 2.開始修改資料 *****************************************/
//		// MembershipService membershipSvc = new MembershipService();
//		membershipSvc.updateMembership(membershipVO);
//
//		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//		model.addAttribute("success", "- (修改成功)");
//		membershipVO = membershipSvc.getOneMembership(Integer.valueOf(membershipVO.getMemId()));
//		model.addAttribute("membershipVO", membershipVO);
//		return "back-end/membership/listAllMembership"; // 修改成功後轉交listOneEmp.html
//	}

//	@PostMapping("update_Status")
//	public String update_Status(@RequestParam("memId") int memId,
//	                             @RequestParam("isAccEna") Byte isAccEna,@RequestParam("isPartEna") Byte isPartEna,
//	                             @RequestParam("isHostEna")Byte isHostEna,@RequestParam("isRentEna") Byte isRentEna,
//	                             @RequestParam("isMsgEna")Byte isMsgEna,
//	                             ModelMap model) {
//
//	    /*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
//	    System.out.println("line 246: memId=" + memId + ", status=" + isAccEna + "+" + isPartEna  + "+" + isHostEna + "+" + isRentEna + "+" +isMsgEna);
//
//	    /*************************** 2.開始修改資料 *****************************************/
//	    
//	    MembershipVO membershipVO = membershipSvc.getOneMembership(memId); //getOne直接去查單一會員的id
//	    membershipVO.setIsAccEna(isAccEna);
//	    membershipVO.setIsPartEna(isPartEna);
//	    membershipVO.setIsHostEna(isHostEna);
//	    membershipVO.setIsRentEna(isRentEna);
//	    membershipVO.setIsMsgEna(isMsgEna);
//	   
//
//	    // MembershipService membershipSvc = new MembershipService();
//	    membershipSvc.updateMembership(membershipVO);
//
//	    /*************************** 3.修改完成,準備轉交(Send the Success view) **************/
//	    model.addAttribute("success", "- (修改成功)");
//	    MembershipVO updatedMembershipVO = membershipSvc.getOneMembership(memId);
//	    model.addAttribute("membershipVO", updatedMembershipVO);
//	    return "back-end/membership/listAllMembership";
////	    return "back-end/membership/select_page";
//	}

	// ----------------update_Status(修改單一會員權限狀態)-----------------
	@PostMapping("update_Status")
	public String update_Status(@Valid MembershipVO membershipVO, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// System.out.println("line 246: memId=" + memId + ", status=" + isAccEna + "+"
		// + isPartEna + "+" + isHostEna + "+" + isRentEna + "+" +isMsgEna);

		/*************************** 2.開始修改資料 *****************************************/

//	    ----------------------------------------------------------

		MembershipVO membership = membershipSvc.getOneMembership(membershipVO.getMemId()); // getOne直接去查單一會員的id
		

//		// 如果狀態由 2(啟用) 變更為 1(停用)，表示帳號被封鎖，發送郵件通知
//		if (membershipVO.getIsAccEna() == 1 && membership.getIsAccEna() == 2) {
//			
//			// 如果 Redis 中沒有值，設置 Redis 鍵的值為 "1" 並執行 Redis 的封鎖時間
//			LocalDateTime unlockTime = LocalDateTime.now().plusSeconds(30);
//			
//			
//			// 獲取會員信箱
//			String memberEmail = membershipSvc.getMemberEmailById(membershipVO.getMemId());
//
//			// 檢查信箱是否存在
//			if (memberEmail != null && !memberEmail.isEmpty()) {
//				// 發送信件
//				membershipSvc.sendAccountBlockedEmail(memberEmail);
//			} else {
//				System.out.println("無法取得會員信箱");
//			}
//		}
		
		// 在帳號被停用的地方，調用 scheduleUnlockTask 方法
		if (membershipVO.getIsAccEna() == 1 && membership.getIsAccEna() == 2) {
		    LocalDateTime unlockTime = LocalDateTime.now().plusSeconds(30);
		    String memberEmail = membershipSvc.getMemberEmailById(membershipVO.getMemId());
		    if (memberEmail != null && !memberEmail.isEmpty()) {
		        membershipSvc.sendAccountBlockedEmail(memberEmail);
		        // 調用 scheduleUnlockTask 方法
		        scheduleUnlockTask(membership.getMemAcc(), unlockTime);
		    } else {
		        System.out.println("無法取得會員信箱");
		    }
		       
		    
		}


		membership.setIsAccEna(membershipVO.getIsAccEna());
		membership.setIsPartEna(membershipVO.getIsPartEna());
		membership.setIsHostEna(membershipVO.getIsHostEna());
		membership.setIsRentEna(membershipVO.getIsRentEna());
		membership.setIsMsgEna(membershipVO.getIsMsgEna());
		membership.setBlockStartTime(membershipVO.getBlockStartTime());


//	   ----------------------------------------------------------	    

		// MembershipService membershipSvc = new MembershipService();
		membershipSvc.updateMembership(membership); // 資料庫更新單筆資料

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("membershipVO", membership);
//	    System.out.println(membership);
		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list);

		//return "back-end/membership/listAllMembership";
		return "redirect:/membership/listAllMembership";
	}

	
	// ----------------------定時器------------------------

	// 一個執行緒池
   private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
			
	private void scheduleUnlockTask(String memAcc, LocalDateTime unlockTime) {
	        scheduler.schedule(() -> {
	            // 同時更新 MySQL 中的值
	            MembershipVO membership = membershipSvc.findByMemAcc(memAcc);
	            membership.setIsAccEna((byte) 2);
	            membershipSvc.updateMembership(membership);

	            System.out.println("memAcc : " + memAcc);

	        }, Duration.between(LocalDateTime.now(), unlockTime).toSeconds(), TimeUnit.SECONDS);
	    }

	    // 關閉執行緒池
	    public void closeScheduler() {
	        scheduler.shutdown();
	    }
	    
	 // ----------------------定時器------------------------
	    
	    

	//  -------------------update----------------------
	@PostMapping("update")
	public String update(@Valid MembershipVO membershipVO, BindingResult result, ModelMap model,
			@RequestParam("memPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(membershipVO, result, "memPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] memPic = membershipSvc.getOneMembership(membershipVO.getMemId()).getMemPic();
			membershipVO.setMemPic(memPic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] memPic = multipartFile.getBytes();
				membershipVO.setMemPic(memPic);
			}
		}
		if (result.hasErrors()) {
			return "front-end/membership/update_membership_input";
		}

//		 ------------------MD5加密-------------------
		String hashedPassword = DigestUtils.md5DigestAsHex(membershipVO.getMemPwd().getBytes());
		membershipVO.setMemPwd(hashedPassword);

		/*************************** 2.開始修改資料 *****************************************/
		// MembershipService membershipSvc = new MembershipService();

		membershipSvc.updateMembership(membershipVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		membershipVO = membershipSvc.getOneMembership(Integer.valueOf(membershipVO.getMemId()));
		model.addAttribute("membershipVO", membershipVO);
		return "front-end/membership/listOneMembership"; // 修改成功後轉交listOneEmp.html
	}

//  -------------------delete----------------------
	@PostMapping("delete")
	public String delete(@RequestParam("memId") String memId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		membershipSvc.deleteSignUp(Integer.valueOf(memId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/membership/listAllMembership"; // 刪除完成後轉交listAllEmp.html
	}

//  ------------getOne_For_Display-----------------	
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "員工編號: 請勿空白") @Digits(integer = 4, fraction = 0, message = "員工編號: 請填數字-請勿超過{integer}位數") @Min(value = 1, message = "員工編號: 不能小於{value}") @Max(value = 100, message = "員工編號: 不能超過{value}") @RequestParam("memId") String memId,
			ModelMap model) {

		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		MembershipVO membershipVO = membershipSvc.getOneMembership(Integer.valueOf(memId));

		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list); // for select_page.html 第97 109行用

//		if (signUpVO == null) {
//			model.addAttribute("errorMessage", "查無資料");
//			return "back-end/signup/select_page";
//		}

		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("membershipVO", membershipVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->

//		return "back-end/signup/listOneSignUp";  // 查詢完成後轉交listOneEmp.html
//		return "back-end/membership/select_page"; // 查詢完成後轉交select_page.html由其第128行insert
		return "back-end/membership/listAllMembership"; // listOneEmp.html內的th:fragment="listOneEmp-div
	}

//  -------------------------------------------------	
	private BindingResult removeFieldError(@Valid MembershipVO membershipVO, BindingResult result,
			String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(membershipVO, "membershipVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

//  --------------------------------------------------	
	@ExceptionHandler(value = { ConstraintViolationException.class })
	// @ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ModelAndView handleError(HttpServletRequest req, ConstraintViolationException e, Model model) {
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "<br>");
		}
		// ==== 以下第80~85行是當前面第69行返回
		// /src/main/resources/templates/back-end/emp/select_page.html 第97行 與 第109行 用的
		// ====
//	    model.addAttribute("empVO", new EmpVO());
//    	EmpService empSvc = new EmpService();
		List<MembershipVO> list = membershipSvc.getAll();
		model.addAttribute("membershipListData", list); // for select_page.html 第97 109行用

		String message = strBuilder.toString();
		return new ModelAndView("back-end/membership/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}
}
