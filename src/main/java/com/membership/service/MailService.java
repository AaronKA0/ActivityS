package com.membership.service;

import java.io.IOException;
import java.util.Properties;

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

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	public void sendVerificationCode(String to, String subject, String verificationCode) {
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

			String changePasswordLink = "zuohuo.ddns.net/membership/updatepassword";

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject); // 設定信中的主旨

			// 創建 MimeMultipart 物件，用於儲存文字和圖片內容
			MimeMultipart multipart = new MimeMultipart();

			// 建立 HTML 內容
			MimeBodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(
					" <h2>Zuò huǒ會員密碼變更通知信!</h2>\r\n" + "\r\n" + "   <p>Hello! 我們將協助您重設一組新的密碼</p>\r\n"
							+ "   <p>以下為我們系統寄出的驗證碼</p>\r\n" + "   <p>*********請謹記此密碼********* </p>\r\n" + "   <p>密碼【"
							+ verificationCode + "】</p>\r\n" + "   <p> 提醒您，驗證碼只保留30分鐘，逾時請重新申請喔!</p>\r\n"
							+ "   <p> (密碼已經啟用)成功登入後可以自行更改密碼喔!</p>\r\n" + "   <p> 請點選連結" + changePasswordLink
							+ "進行密碼更改 </p>\r\n" + "   <img src='cid:image1' width='320' height='100' >",
					"text/html; charset=utf-8");

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

	// -------------------------封鎖時寄送信件-------------------------

	public void sendAccountBlockedEmail(String to, String subject) {
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
			htmlPart.setContent(" <h2>Zuò huǒ帳戶停用48小時通知信!</h2>\r\n" + "\r\n" + " <p>Hello! 由於您的帳號被檢舉過於頻繁</p>\r\n"
					+ " <p>為維護平台用戶權益，我們將您的帳戶封鎖。</p>\r\n" + "   <p>**************************************************************</p>\r\n"
					+ "   <h2>提醒您，我們將您的帳戶停用48小時</h2>\r\n" + "   <h2>提醒您，我們將您的帳戶停用48小時</h2>\r\n"
					+ "   <h2>提醒您，我們將您的帳戶停用48小時</h2>\r\n" + "   <p></p>\r\n"
					+ "   <p>**************************************************************</p>\r\n"
					+ "   <p>【提醒您，收到信件請自行推算48小時後可以登入時間】</p>\r\n" + " <p> 如有疑慮請您透過官方的聯絡我們，我們會盡快與您聯繫，謝謝!</p>\r\n"
					+ " <img src='cid:image1' width='320' height='100' >", "text/html; charset=utf-8");

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

}
