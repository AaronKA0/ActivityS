package com.postreport.controller;

import java.io.IOException;
import java.util.HashSet;
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

import com.membership.model.MembershipVO;
import com.membership.service.MembershipService;
import com.notify.service.NotifyNow;
import com.post.Post;
import com.post.PostService;
import com.postreport.model.PostReportVO;
import com.postreport.service.PostReportService;

@Controller
@RequestMapping("/postreport")
public class PostReportController {

	@Autowired
	PostReportService postReportSvc;
	
	@Autowired
	PostService postSvc;
	
	@Autowired
	MembershipService membershipSvc;
	
	@Autowired
	NotifyNow notifyNow;

	@GetMapping("addPostReport")
	public String addPostReport(ModelMap model) {
		PostReportVO postReportVO = new PostReportVO();
		model.addAttribute("postReportVO", postReportVO);
		return "front-end/postreport/addPostReport";
	}

//-------------------------insert-----------------------	
	@PostMapping("insert")
	public String insert(@Valid PostReportVO postReportVO, BindingResult result, ModelMap model,
			@RequestParam("repPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(postReportVO, result, "repPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				postReportVO.setRepPic(buf);
			}
		}
//		if (result.hasErrors() || parts[0].isEmpty()) {
//			return "back-end/postreport/addPostReport";
//		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		postReportSvc.addPostReport(postReportVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/postreport/addPostReport"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
	}

//----------------------getOne_For_Update-----------------------		
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		PostReportVO postReportVO = postReportSvc.getOnePostReport(Integer.valueOf(repId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("postReportVO", postReportVO);
		return "back-end/postreport/update_postreport_input"; // 查詢完成後轉交update_emp_input.html
	}

//  ----------------getOne_For_Status(查會員單一帳戶權限的狀態)-----------------
	@PostMapping("getOne_For_Status")
	public String status_For_Update(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		PostReportVO postReportVO = postReportSvc.getStatusPostReport(Integer.valueOf(repId));
		System.out.println("postReportVO" + postReportVO);
		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list); // for select_page.html 第97 109行用

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("postReportVO", postReportVO);
		model.addAttribute("getOne_For_Status", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
//		return "back-end/membership/update_membership_input"; // 查詢完成後轉交update_emp_input.html
//		return "back-end/postreport/select_page";
		return "back-end/postreport/listAllPostReport";
	}

//  ----------------update_Status(修改單一會員權限狀態)-----------------
	@PostMapping("/update_Status")
	public String update_Status(@Valid PostReportVO postReportVO, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// System.out.println("line 246: memId=" + memId + ", status=" + isAccEna + "+"
		// + isPartEna + "+" + isHostEna + "+" + isRentEna + "+" +isMsgEna);

		/*************************** 2.開始修改資料 *****************************************/

		PostReportVO postReport = postReportSvc.getOnePostReport(postReportVO.getRepId()); // getOne直接去查單一會員的id
		postReport.setRepStatus(postReportVO.getRepStatus());

		// MembershipService membershipSvc = new MembershipService();
		postReportSvc.updatePostReport(postReport); // 資料庫更新單筆資料

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("postReportVO", postReport);
		// System.out.println(membership);
		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list);
		
		
		// delete the post if the post report status is set to 3 (審核未通過)
		Post post = postSvc.getPost(postReport.getReporteeId(), postReport.getPostId());
		
		if(postReport.getRepStatus() == 2) {
			post.setPostStatus((byte)3);
			// send notification after post report is successful
			MembershipVO memberA = membershipSvc.getOneMembership(postReport.getMemId());
			MembershipVO memberB = membershipSvc.getOneMembership(postReport.getReporteeId());

			Set<MembershipVO> members = new HashSet<MembershipVO>();
			members.add(memberA);
			notifyNow.sendNotifyNow(members, "系統通知", "收到您的檢舉後，我們決定刪除" + memberB.getMemUsername() + "的貼文");
			
			Set<MembershipVO> members2 = new HashSet<MembershipVO>();
			members2.add(memberB);
			notifyNow.sendNotifyNow(members2, "系統通知", "在收到檢舉並審核您的貼文後，我們認為它違反了我們的標準並刪除了您的貼文");
		} else if(postReport.getRepStatus() == 3){
			post.setPostStatus((byte)2);
		}
		postSvc.editPost(post);		
		
		return "back-end/postreport/listAllPostReport";
	}

//-------------------------update---------------------------		
	@PostMapping("update")
	public String update(@Valid PostReportVO postReportVO, BindingResult result, ModelMap model,
			@RequestParam("repPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(postReportVO, result, "repPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] repPic = postReportSvc.getOnePostReport(postReportVO.getRepId()).getRepPic();
			postReportVO.setRepPic(repPic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] repPic = multipartFile.getBytes();
				postReportVO.setRepPic(repPic);
			}
		}
		if (result.hasErrors()) {
			return "back-end/postreport/update_postreport_input";
		}
		/*************************** 2.開始修改資料 *****************************************/
		// MembershipService membershipSvc = new MembershipService();
		postReportSvc.updatePostReport(postReportVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		postReportVO = postReportSvc.getOnePostReport(Integer.valueOf(postReportVO.getRepId()));
		model.addAttribute("postReportVO", postReportVO);
		
		return "back-end/postreport/listOnePostReport"; // 修改成功後轉交listOneEmp.html
	}

//-------------------------delete---------------------------			
	@PostMapping("delete")
	public String delete(@RequestParam("repId") String repId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		postReportSvc.deletePostReport(Integer.valueOf(repId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/postreport/listAllPostReport"; // 刪除完成後轉交listAllEmp.html
	}

//---------------------getOne_For_Display------------------------		
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "員工編號: 請勿空白") @Digits(integer = 4, fraction = 0, message = "員工編號: 請填數字-請勿超過{integer}位數") @Min(value = 1, message = "員工編號: 不能小於{value}") @Max(value = 100, message = "員工編號: 不能超過{value}") @RequestParam("repId") String repId,
			ModelMap model) {

		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		PostReportVO postReportVO = postReportSvc.getOnePostReport(Integer.valueOf(repId));

		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list); // for select_page.html 第97 109行用

		if (postReportVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/postreport/select_page";
		}

		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("postReportVO", postReportVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->

//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
//		return "back-end/postreport/select_page"; // 查詢完成後轉交select_page.html由其第128行insert
		return "back-end/postreport/listAllPostReport"; // listOneEmp.html內的th:fragment="listOneEmp-div
	}

	private BindingResult removeFieldError(@Valid PostReportVO postReportVO, BindingResult result,
			String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(postReportVO, "postReportVO");
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
		List<PostReportVO> list = postReportSvc.getAll();
		model.addAttribute("postreportListData", list); // for select_page.html 第97 109行用

		String message = strBuilder.toString();
		return new ModelAndView("back-end/postreport/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}
