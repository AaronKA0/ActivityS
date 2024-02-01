package com.actreport.controller;//package com.example.report.controller;
//
//import com.example.report.model.ActivityReportVO;
//import com.example.report.service.impl.ActivityReportService;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.Part;
//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Timestamp;
//import java.util.LinkedList;
//import java.util.List;
//
//@MultipartConfig
//public class OldActivityReportController extends HttpServlet {
//
//	@Override
//	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		doPost(req, res);
//	}
//
//	@Override
//	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//		req.setCharacterEncoding("UTF-8");
//		String action = req.getParameter("action");
//
//		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			String str = req.getParameter("repId");
//			if (str == null || (str.trim()).length() == 0) {
//				errorMsgs.add("請輸入活動編號");
//			}
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/activityreport/select_page_ActivityReport.jsp");
//				failureView.forward(req, res);
//				return;// 程式中斷
//			}
//
//			Integer repId = null;
//			try {
//				repId = Integer.valueOf(str);
//			} catch (Exception e) {
//				errorMsgs.add("活動編號格式不正確");
//			}
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/activityreport/select_page_ActivityReport.jsp");
//				failureView.forward(req, res);
//				return;// 程式中斷
//			}
//
//			/*************************** 2.開始查詢資料 *****************************************/
//			ActivityReportService arSvc = new ActivityReportService();
//			ActivityReportVO activityReportVO = arSvc.findByPrimaryKey(repId);
//			if (activityReportVO == null) {
//				errorMsgs.add("查無資料");
//			}
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/activityreport/select_page_ActivityReport.jsp");
//				failureView.forward(req, res);
//				return;// 程式中斷
//			}
//
//			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//			req.setAttribute("activityReportVO", activityReportVO); // 資料庫取出的activityReportVO物件,存入req
//			String url = "/activityreport/listOneActivityReport.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 來自listAll.jsp的請求
//			successView.forward(req, res);
//		}
//
//		if ("getOne_For_Update".equals(action)) { // 來自listAll.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			Integer repId = Integer.valueOf(req.getParameter("repId"));
//
//			/*************************** 2.開始查詢資料 *****************************************/
//			ActivityReportService arSvc = new ActivityReportService();
//			ActivityReportVO activityReportVO = arSvc.findByPrimaryKey(repId);
//
//			/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
//			req.setAttribute("activityReportVO", activityReportVO); // 資料庫取出的activityReportVO物件,存入req
//			String url = "/activityreport/update_ActivityReport_input.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
//			successView.forward(req, res);
//		}
//
//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//
//			Integer repId = Integer.valueOf(req.getParameter("repId"));
//			Integer actId = Integer.valueOf(req.getParameter("actId"));
//			Integer memId = Integer.valueOf(req.getParameter("memId"));
//			Integer empId = Integer.valueOf(req.getParameter("empId"));
//			String repTitle = req.getParameter("repTitle");
//			String repContent = req.getParameter("repContent");
////			Integer repPic = Integer.valueOf(req.getParameter("repPic"));
//			Timestamp repTime = Timestamp.valueOf(req.getParameter("repTime"));
//			Byte repStatus = Byte.valueOf(req.getParameter("repStatus").trim());
//
//			ActivityReportVO activityReportVO = new ActivityReportVO();
//			activityReportVO.setRepId(repId);
//			activityReportVO.setActId(actId);
//			activityReportVO.setMemId(memId);
//			activityReportVO.setEmpId(empId);
//			activityReportVO.setRepTitle(repTitle);
//			activityReportVO.setRepContent(repContent);
////			activityReportVO.setRepPic(repPic);
//			activityReportVO.setRepTime(repTime);
//			activityReportVO.setRepStatus(repStatus);
//
//			// Send the use back to the form, if there were errors
////			if (!errorMsgs.isEmpty()) {
////				req.setAttribute("empVO", empVO); //// 含有輸入格式錯誤的empVO物件,也存入req
////				RequestDispatcher failureView = req.getRequestDispatcher("/emp/update_emp_input.jsp");
////				failureView.forward(req, res);
////				return; //程式中斷
////			}
//
//			/*************************** 2.開始查詢資料 *****************************************/
//			ActivityReportService arSvc = new ActivityReportService();
//			arSvc.update(activityReportVO);
//
//			/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
//			req.setAttribute("activityReportVO", activityReportVO); // 資料庫update成功後,正確的的empVO物件,存入req
//			String url = "/activityreport/listOneActivityReport.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//			successView.forward(req, res);
//		}
//
//		if ("insert".equals(action)) { // 來自addEmp.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
//			Integer actId = null;
//			try {
//				actId = Integer.valueOf(req.getParameter("actId").trim());
//			} catch (NumberFormatException e) {
//				errorMsgs.add("填活動編號");
//			}
//			Integer memId = null;
//			try {
//				memId = Integer.valueOf(req.getParameter("memId").trim());
//			} catch (NumberFormatException e) {
//				errorMsgs.add("填會員編號");
//			}
//			Integer empId = null;
//			try {
//				empId = Integer.valueOf(req.getParameter("empId").trim());
//			} catch (NumberFormatException e) {
//				errorMsgs.add("填員工編號");
//			}
//
//			String repTitle = req.getParameter("repTitle").trim();
//			if (repTitle == null || repTitle.trim().length() == 0) {
//				errorMsgs.add("請填檢舉標題");
//			}
//
//			String repContent = req.getParameter("repContent").trim();
//			if (repContent == null || repContent.trim().length() == 0) {
//				errorMsgs.add("請填檢舉內容");
//			}
//
//			byte[] repPic = null;
//
//			Part filePart = req.getPart("repPic");
//			if (filePart != null) {
//				// 處理檔案
//				InputStream fileContent = filePart.getInputStream();
//				repPic = fileContent.readAllBytes();
//				fileContent.close();
//			} else {
//				System.out.println("filePart is null");
//			}
//
//			ActivityReportVO activityReportVO = new ActivityReportVO();
//			activityReportVO.setActId(actId);
//			activityReportVO.setMemId(memId);
//			activityReportVO.setEmpId(empId);
//			activityReportVO.setRepTitle(repTitle);
//			activityReportVO.setRepContent(repContent);
//			activityReportVO.setRepPic(repPic);
//			activityReportVO.setRepTime(new Timestamp(System.currentTimeMillis()));
//
//			// Send the use back to the form, if there were errors
//			if (!errorMsgs.isEmpty()) {
//				req.setAttribute("activityReportVO", activityReportVO); // 含有輸入格式錯誤的empVO物件,也存入req
//				RequestDispatcher failureView = req.getRequestDispatcher("/activityreport/addActivityReport.jsp");
//				failureView.forward(req, res);
//				return;
//			}
//
//			/*************************** 2.開始新增資料 *****************************************/
//			ActivityReportService arSvc = new ActivityReportService();
//			arSvc.insert(activityReportVO);
//
//			/*************************** 3.新增完成,準備轉交(Send the Success view) *************/
//			String url = "/activityreport/listAllActivityReport.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//			successView.forward(req, res);
//		}
//
//		if ("delete".equals(action)) { // 來自listAllEmp.jsp
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			/*************************** 1.接收請求參數 ***************************************/
//			Integer repId = Integer.valueOf(req.getParameter("repId"));
//
//			/*************************** 2.開始刪除資料 ***************************************/
//			ActivityReportService arSvc = new ActivityReportService();
//			arSvc.delete(repId);
//
//			/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
//			String url = "/activityreport/listAllActivityReport.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
//			successView.forward(req, res);
//		}
//	}
//
//}
