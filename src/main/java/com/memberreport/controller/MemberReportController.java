package com.memberreport.controller;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.memberreport.service.MemberReportService;
import com.memberreport.model.MemberReportVO;

@Controller
@RequestMapping("/memberreport")
public class MemberReportController {

	@Autowired
	MemberReportService memberReportSvc;

	@GetMapping("addMemberReport")
	public String addMemberReport(ModelMap model) {
		MemberReportVO memberReportVO = new MemberReportVO();
		model.addAttribute("memberReportVO", memberReportVO);
		return "front-end/memberreport/addMemberReport";
	}

//------------------------insert-------------------------
	@PostMapping("insert")
	public String insert(@Valid MemberReportVO memberReportVO, BindingResult result, ModelMap model,
			@RequestParam("repPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(memberReportVO, result, "repPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				memberReportVO.setRepPic(buf);
			}
		}
//		if (result.hasErrors() || parts[0].isEmpty()) {
//		 return "back-end/memberreport/addMemberReport";
//		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		memberReportSvc.addMemberReport(memberReportVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/memberreport/addMemberReport"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}

//------------------------getOne_For_Update-------------------------	
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		MemberReportVO memberReportVO = memberReportSvc.getOneMemberReport(Integer.valueOf(repId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("memberReportVO", memberReportVO);
		return "back-end/memberreport/update_memberreport_input"; // 查詢完成後轉交update_emp_input.html
	}

//  ----------------getOne_For_Status(查會員單一帳戶權限的狀態)-----------------
	@PostMapping("getOne_For_Status")
	public String status_For_Update(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		MemberReportVO memberReportVO = memberReportSvc.getStatusMemberReport(Integer.valueOf(repId));
		System.out.println("memberReportVO" + memberReportVO);
		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list); // for select_page.html 第97 109行用

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("memberReportVO", memberReportVO);
		model.addAttribute("getOne_For_Status", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
//		return "back-end/membership/update_membership_input"; // 查詢完成後轉交update_emp_input.html
//		return "back-end/memberreport/select_page";
		return "back-end/memberreport/listAllMemberReport";
	}

//  ----------------update_Status(修改單一會員權限狀態)-----------------
	@PostMapping("/update_Status")
	public String update_Status(@Valid MemberReportVO memberReportVO, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// System.out.println("line 246: memId=" + memId + ", status=" + isAccEna + "+"
		// + isPartEna + "+" + isHostEna + "+" + isRentEna + "+" +isMsgEna);

		/*************************** 2.開始修改資料 *****************************************/

		MemberReportVO memberReport = memberReportSvc.getOneMemberReport(memberReportVO.getRepId()); // getOne直接去查單一會員的id
		memberReport.setRepStatus(memberReportVO.getRepStatus());

		// MembershipService membershipSvc = new MembershipService();
		memberReportSvc.updateMemberReport(memberReport); // 資料庫更新單筆資料

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("memberReportVO", memberReport);
		// System.out.println(membership);
		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list);
		return "back-end/memberreport/listAllMemberReport";
	}

//  ---------------------update------------------------
	@PostMapping("update")
	public String update(@Valid MemberReportVO memberReportVO, BindingResult result, ModelMap model,
			@RequestParam("repPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(memberReportVO, result, "repPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] repPic = memberReportSvc.getOneMemberReport(memberReportVO.getRepId()).getRepPic();
			memberReportVO.setRepPic(repPic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] repPic = multipartFile.getBytes();
				memberReportVO.setRepPic(repPic);
			}
		}
		if (result.hasErrors()) {
			return "back-end/memberreport/update_memberreport_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// MembershipService membershipSvc = new MembershipService();
		memberReportSvc.updateMemberReport(memberReportVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		memberReportVO = memberReportSvc.getOneMemberReport(Integer.valueOf(memberReportVO.getRepId()));
		model.addAttribute("memberReportVO", memberReportVO);
		return "back-end/memberreport/listOneMemberReport"; // 修改成功後轉交listOneEmp.html
	}

//  ----------------------delete------------------------	
	@PostMapping("delete")
	public String delete(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		memberReportSvc.deleteMemberReport(Integer.valueOf(repId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/memberreport/listAllMemberReport"; // 刪除完成後轉交listAllEmp.html
	}

//  ----------------------getOne_For_Display------------------------			
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "員工編號: 請勿空白") @Digits(integer = 4, fraction = 0, message = "員工編號: 請填數字-請勿超過{integer}位數") @Min(value = 1, message = "員工編號: 不能小於{value}") @Max(value = 100, message = "員工編號: 不能超過{value}") @RequestParam("repId") String repId,
			ModelMap model) {

		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		MemberReportVO memberReportVO = memberReportSvc.getOneMemberReport(Integer.valueOf(repId));

		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list); // for select_page.html 第97 109行用

//		if (memberReportVO == null) {
//			model.addAttribute("errorMessage", "查無資料");
//			return "back-end/memberreport/select_page";
//		}

		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("memberReportVO", memberReportVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->

//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
//		return "back-end/memberreport/select_page"; // 查詢完成後轉交select_page.html由其第128行insert
		return "back-end/memberreport/listAllMemberReport"; // listOneEmp.html內的th:fragment="listOneEmp-div
	}

	private BindingResult removeFieldError(@Valid MemberReportVO memberReportVO, BindingResult result,
			String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(memberReportVO, "memberReportVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

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
		List<MemberReportVO> list = memberReportSvc.getAll();
		model.addAttribute("memberreportListData", list); // for select_page.html 第97 109行用

		String message = strBuilder.toString();
		return new ModelAndView("back-end/memberreport/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}
