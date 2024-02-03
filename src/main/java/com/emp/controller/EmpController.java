package com.emp.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.emp.model.EmpVO;
import com.dept.model.DeptService;
import com.dept.model.DeptVO;
import com.emp.model.EmpService;

@Controller
@RequestMapping("/emp")
public class EmpController {

	@Autowired
	EmpService empSvc;

	@Autowired
	DeptService deptSvc;

	@Autowired
	PasswordEncoder passwordEncoder;

	/*
	 * This method will serve as addEmp.html handler.
	 */
	@GetMapping("addEmp")
	public String addEmp(ModelMap model) {
		EmpVO empVO = new EmpVO();
		model.addAttribute("empVO", empVO);
		return "back-end/emp/addEmp";
	}

	/*
	 * This method will be called on addEmp.html form submission, handling POST
	 * request It also validates the user input
	 */
	@PostMapping("insert")
	public String insert(@Valid EmpVO empVO, BindingResult result, ModelMap model,
			@RequestParam("empPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(empVO, result, "empPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] buf = multipartFile.getBytes();
				empVO.setEmpPic(buf);
			}
		}
		if (result.hasErrors() || parts[0].isEmpty()) {
			return "back-end/emp/addEmp";
		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		// 加密密碼並且開始新增
		String encodedPassword = passwordEncoder.encode(empVO.getEmpPwd());
		empVO.setEmpPwd(encodedPassword);
		empSvc.addEmp(empVO);

		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<EmpVO> list = empSvc.getAll();
		model.addAttribute("empListData", list);
		model.addAttribute("success", "- (新增成功)");
		return "redirect:/emp/listAllEmp"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")

	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("empId") String empId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		EmpVO empVO = empSvc.getOneEmp(Integer.valueOf(empId));
		empVO.setEmpPwd(""); //設置密碼為空
		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("empVO", empVO);
		return "back-end/emp/update_emp_input"; // 查詢完成後轉交update_emp_input.html
	}

	/*
	 * This method will be called on update_emp_input.html form submission, handling
	 * POST request It also validates the user input
	 */
	@PostMapping("update")
	public String update(@Valid EmpVO empVO, BindingResult result, ModelMap model,
			@RequestParam("empPic") MultipartFile[] parts) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
		result = removeFieldError(empVO, result, "empPic");

		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
			// EmpService empSvc = new EmpService();
			byte[] empPic = empSvc.getOneEmp(empVO.getEmpId()).getEmpPic();
			empVO.setEmpPic(empPic);
		} else {
			for (MultipartFile multipartFile : parts) {
				byte[] empPic = multipartFile.getBytes();
				empVO.setEmpPic(empPic);
			}
		}
		if (result.hasErrors()) {

			return "back-end/emp/update_emp_input";
		}



		/*************************** 2.開始修改資料 *****************************************/
		// EmpService empSvc = new EmpService();
		// 加密新密碼
		String encodedPassword = passwordEncoder.encode(empVO.getEmpPwd());
		empVO.setEmpPwd(encodedPassword);
		empSvc.addEmp(empVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		empVO = empSvc.getOneEmp(Integer.valueOf(empVO.getEmpId()));
		model.addAttribute("empVO", empVO);
		model.addAttribute("getOne_For_Display", "true");
		return "back-end/emp/listOneEmp"; // 修改成功後轉交listOneEmp.html
	}

	/*
	 * This method will be called on listAllEmp.html form submission, handling POST
	 * request
	 */
	@PostMapping("delete")
	public String delete(@RequestParam("empId") String empId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		empSvc.deleteEmp(Integer.valueOf(empId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<EmpVO> list = empSvc.getAll();
		model.addAttribute("empListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/emp/listAllEmp"; // 刪除完成後轉交listAllEmp.html
	}

	/*
	 * 第一種作法 Method used to populate the List Data in view. 如 : <form:select
	 * path="deptno" id="deptno" items="${deptListData}" itemValue="deptno"
	 * itemLabel="dname" />
	 */
	@ModelAttribute("deptListData")
	protected List<DeptVO> referenceListData() {
		// DeptService deptSvc = new DeptService();
		List<DeptVO> list = deptSvc.getAll();
		return list;
	}

	/*
	 * 【 第二種作法 】 Method used to populate the Map Data in view. 如 : <form:select
	 * path="deptno" id="deptno" items="${depMapData}" />
	 */
	@ModelAttribute("deptMapData") //
	protected Map<Integer, String> referenceMapData() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(1, "超級管理員");
		map.put(2, "活動管理員");
		map.put(3, "場地管理員");
		map.put(4, "會員管理員");
		map.put(5, "員工管理員");
		return map;
	}

	// 去除BindingResult中某個欄位的FieldError紀錄
	public BindingResult removeFieldError(EmpVO empVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(empVO, "empVO");
		for (FieldError fieldError : errorsListToKeep) {
			result.addError(fieldError);
		}
		return result;
	}

	/*
	 * This method will be called on select_page.html form submission, handling POST
	 * request
	 */
	@PostMapping("listEmps_ByCompositeQuery")
	public String listAllEmp(HttpServletRequest req, Model model) {
		Map<String, String[]> map = req.getParameterMap();
		List<EmpVO> list = empSvc.getAll(map);
		model.addAttribute("empListData", list); // for listAllEmp.html 第85行用
		return "back-end/emp/listAllEmp";
	}

}