package com.faq.controller;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import org.springframework.web.servlet.ModelAndView;

import com.faq.service.FaqService;
import com.faq.model.FaqVO;

import org.springframework.mail.SimpleMailMessage;

@Controller
@RequestMapping("/faq")
public class FaqController {

	@Autowired
	FaqService faqSvc;

	@GetMapping("addFaq")
	public String addFaq(ModelMap model) {
		FaqVO faqVO = new FaqVO();
		model.addAttribute("faqVO", faqVO);
		return "back-end/faq/addFaq";
	}

	@GetMapping("faqs")
	public String faqs(ModelMap model) {
		FaqVO faqVO = new FaqVO();
		model.addAttribute("faqVO", faqVO);
		return "front-end/faq/faqs";
	}

//	@GetMapping("faqs")
//	public String faqs2(ModelMap model) {
//		return "front-end/faq/faqs";
//	}

//  ----------------------insert-------------------------
	@PostMapping("insert")
	public String insert(@Valid FaqVO faqVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(faqVO, result, "memPic");

//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的圖片時
//			model.addAttribute("errorMessage", "員工照片: 請上傳照片");
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] buf = multipartFile.getBytes();
//				faqVO.setMemPic(buf);
//			}
//		}
//		if (result.hasErrors() || parts[0].isEmpty()) {
//			return "back-end/faq/addFaq";
//		}
		/*************************** 2.開始新增資料 *****************************************/
		// EmpService empSvc = new EmpService();
		faqSvc.addFaq(faqVO);
		/*************************** 3.新增完成,準備轉交(Send the Success view) **************/
		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list);

		return "redirect:/faq/listAllFaq"; // 新增成功後重導至IndexController_inSpringBoot.java的第50行@GetMapping("/emp/listAllEmp")
		// return "redirect:/faq/faqs";
	}

	// ----------------getOne_For_Update-----------------
	@PostMapping("getOne_For_Update")
	public String getOne_For_Update(@RequestParam("faqId") String faqId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		FaqVO faqVO = faqSvc.getOneFaq(Integer.valueOf(faqId));

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("faqVO", faqVO);
		return "back-end/faq/update_faq_input"; // 查詢完成後轉交update_emp_input.html
	}

//  ----------------getOne_For_Status-----------------
	@PostMapping("getOne_For_Status")
	public String status_For_Update(@RequestParam("faqId") String faqId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/

		/*************************** 2.開始查詢資料 *****************************************/
		// EmpService empSvc = new EmpService();
		FaqVO faqVO = faqSvc.getStatusFaq(Integer.valueOf(faqId));
		System.out.println("faqVO" + faqVO);
		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list); // for select_page.html 第97 109行用

		/*************************** 3.查詢完成,準備轉交(Send the Success view) **************/
		model.addAttribute("faqVO", faqVO);
		model.addAttribute("getOne_For_Status", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->
//		return "back-end/membership/update_membership_input"; // 查詢完成後轉交update_emp_input.html
//		return "back-end/faq/select_page";
		return "back-end/faq/listAllFaq";
	}

//  ------------------update_Status-------------------
	@PostMapping("/update_Status")
	public String update_Status(@Valid FaqVO faqVO, ModelMap model) {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// System.out.println("line 246: memId=" + memId + ", status=" + isAccEna + "+"
		// + isPartEna + "+" + isHostEna + "+" + isRentEna + "+" +isMsgEna);

		/*************************** 2.開始修改資料 *****************************************/

		FaqVO faq = faqSvc.getOneFaq(faqVO.getFaqId()); // getOne直接去查單一會員的id
		faq.setFaqQue(faqVO.getFaqQue());
		faq.setFaqAns(faqVO.getFaqAns());

		// MembershipService membershipSvc = new MembershipService();
		faqSvc.updateFaq(faq); // 資料庫更新單筆資料

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("faqVO", faq);
		// System.out.println(membership);
		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list);
		return "back-end/faq/listAllFaq";
	}

//  ------------------------update--------------------------
	@PostMapping("update")
	public String update(@Valid FaqVO faqVO, BindingResult result, ModelMap model) throws IOException {

		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		// 去除BindingResult中upFiles欄位的FieldError紀錄 --> 見第172行
//		result = removeFieldError(faqVO, result, "memPic");

//		if (parts[0].isEmpty()) { // 使用者未選擇要上傳的新圖片時
//			// EmpService empSvc = new EmpService();
//			byte[] memPic = faqSvc.getOneFaq(faqVO.getFaqId()).getMemPic();
//			faqVO.setMemPic(memPic);
//		} else {
//			for (MultipartFile multipartFile : parts) {
//				byte[] memPic = multipartFile.getBytes();
//				faqVO.setMemPic(memPic);
//			}
//		}
//		if (result.hasErrors()) {
//			return "back-end/faq/update_faq_input";
//		}
		/*************************** 2.開始修改資料 *****************************************/
		// MembershipService membershipSvc = new MembershipService();
		faqSvc.updateFaq(faqVO);

		/*************************** 3.修改完成,準備轉交(Send the Success view) **************/
		model.addAttribute("success", "- (修改成功)");
		faqVO = faqSvc.getOneFaq(Integer.valueOf(faqVO.getFaqId()));
		model.addAttribute("faqVO", faqVO);
		return "back-end/faq/listOneFaq"; // 修改成功後轉交listOneEmp.html
	}

//  ------------------------delete--------------------------	
	@PostMapping("delete")
	public String delete(@RequestParam("faqId") String faqId, ModelMap model) {
		/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 ************************/
		/*************************** 2.開始刪除資料 *****************************************/
		// EmpService empSvc = new EmpService();
		faqSvc.deleteFaq(Integer.valueOf(faqId));
		/*************************** 3.刪除完成,準備轉交(Send the Success view) **************/
		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list);
		model.addAttribute("success", "- (刪除成功)");
		return "back-end/faq/listAllFaq"; // 刪除完成後轉交listAllEmp.html
	}

//  ----------------------getOne_For_Display--------------------------		
	@PostMapping("getOne_For_Display")
	public String getOne_For_Display(/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
	@NotEmpty(message = "員工編號: 請勿空白") @Digits(integer = 4, fraction = 0, message = "員工編號: 請填數字-請勿超過{integer}位數") @Min(value = 1, message = "員工編號: 不能小於{value}") @Max(value = 100, message = "員工編號: 不能超過{value}") @RequestParam("faqId") String faqId,
			ModelMap model) {

		/***************************
		 * 2.開始查詢資料
		 *********************************************/
//		EmpService empSvc = new EmpService();
		FaqVO faqVO = faqSvc.getOneFaq(Integer.valueOf(faqId));

		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list); // for select_page.html 第97 109行用

		if (faqVO == null) {
			model.addAttribute("errorMessage", "查無資料");
			return "back-end/faq/select_page";
		}

		/***************************
		 * 3.查詢完成,準備轉交(Send the Success view)
		 *****************/
		model.addAttribute("faqVO", faqVO);
		model.addAttribute("getOne_For_Display", "true"); // 旗標getOne_For_Display見select_page.html的第126行 -->

//		return "back-end/emp/listOneEmp";  // 查詢完成後轉交listOneEmp.html
//		return "back-end/faq/select_page"; // 查詢完成後轉交select_page.html由其第128行insert
		return "back-end/faq/listAllFaq"; // listOneEmp.html內的th:fragment="listOneEmp-div
	}

//  ---------------------contact_us(聯絡我們)--------------------------	
	@PostMapping("contact_us")
	public String contact_us(@RequestParam String contactName, @RequestParam String contactEmail,
			@RequestParam String contactSubject, @RequestParam String contactMessage, Model model) {

		// 從表單數據中獲得資料
		String name = contactName;
		String email = contactEmail;
		String subject = contactSubject;
		String message = contactMessage;

		// 郵件內容
		String mailContent = "【做伙-聯絡我們】聯絡者姓名 : " + name + "\n";
		mailContent += "【做伙-聯絡我們】聯絡者電子郵件 : " + email + "\n";
		mailContent += "【做伙-聯絡我們】聯絡者主旨 : " + subject + "\n";
		mailContent += "【做伙-聯絡我們】聯絡者訊息 : " + message + "\n";

		// 發送郵件
		sendEmail(contactName, contactEmail, "ballbrotherx87@gmail.com", contactSubject, contactMessage);

		model.addAttribute("name", name);
		model.addAttribute("email", email);
		model.addAttribute("subject", subject);
		model.addAttribute("message", message);

		return "front-end/faq/faqs";

	}

	public void sendEmail(String From, String Email, String to, String subject, String messageText) {

		SimpleMailMessage messages = new SimpleMailMessage();
		messages.setFrom(From);
		messages.setTo(to);
		messages.setSubject(subject);
		messages.setText(messageText);

		try {
			// 設定使用SSL連線至 Gmail smtp Server
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");

			final String myGmail = "ballbrotherx87@gmail.com"; // 寄出者的信箱
//			final String myGmail_password = "fgygcmheubhcikrz"; // 寄出者密碼
			final String myGmail_password = "fgygcmheubhcikrz";

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); // 設定信中的主旨

			// 創建 MimeMultipart 物件，用於儲存文字和圖片內容
			MimeMultipart multipart = new MimeMultipart();

			// 建立 HTML 內容
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(" <h2>Zuò huǒ聯絡我們信件通知信!</h2>\r\n" + "\r\n"
					+ "   <p>Hello! 我們收到了一封來自Zuò huǒ聯絡我們的信件</p>\r\n" + "   <p>============以下為信件的資訊為============</p>\r\n"
					+ "   <p>【做伙-聯絡我們】聯絡者姓名 : " + From + "</p>\r\n" + "   <p>【做伙-聯絡我們】聯絡者電子郵件 : " + Email + "</p>\r\n"
					+ "   <p>【做伙-聯絡我們】聯絡者主旨 : " + subject + "</p>\r\n" + "   <p>【做伙-聯絡我們】聯絡者訊息 : " + messageText
					+ " </p>\r\n" + "   <p></p>\r\n" + "   <p>========================================</p>\r\n"
					+ "   <img src='cid:image1' width='320' height='100' >", "text/html; charset=utf-8");

			// 建立 MimeBodyPart 物件，用於儲存圖片
			String imagePath = "/static/front-end/16/images/ZuoHuo2.jpg";
			MimeBodyPart imagePart = new MimeBodyPart();
			imagePart.attachFile(new ClassPathResource(imagePath).getFile()); // 從Resource直接去找底下的資料
			imagePart.setContentID("image1");

			// 將文字和圖片的部分加入 MimeMultipart
			multipart.addBodyPart(htmlPart);
			multipart.addBodyPart(imagePart);

			// 將 MimeMultipart 設定到訊息中
			message.setContent(multipart);

			System.out.println("郵件已成功寄送！");

			Transport.send(message);
			System.out.println("傳送成功!");
		} catch (MessagingException | IOException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}

//  -----------------------------------------------------------  
	private BindingResult removeFieldError(@Valid FaqVO faqVO, BindingResult result, String removedFieldname) {
		List<FieldError> errorsListToKeep = result.getFieldErrors().stream()
				.filter(fieldname -> !fieldname.getField().equals(removedFieldname)).collect(Collectors.toList());
		result = new BeanPropertyBindingResult(faqVO, "faqVO");
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
		List<FaqVO> list = faqSvc.getAll();
		model.addAttribute("faqListData", list); // for select_page.html 第97 109行用

		String message = strBuilder.toString();
		return new ModelAndView("back-end/faq/select_page", "errorMessage", "請修正以下錯誤:<br>" + message);
	}

}
