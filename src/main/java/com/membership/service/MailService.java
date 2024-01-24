//package com.mail;
//
//import java.util.Properties;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//
//public class MailService {
//
//	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
//	public void sendMail(String to, String subject, String messageText) {
//
//		try {
//			// 設定使用SSL連線至 Gmail smtp Server
//			Properties props = new Properties();
//			props.put("mail.smtp.host", "smtp.gmail.com");
//			props.put("mail.smtp.socketFactory.port", "465");
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.port", "465");
//
//			// ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
//			// ●1) 登入你的Gmail的:
//			// ●2) 點選【管理你的 Google 帳戶】
//			// ●3) 點選左側的【安全性】
//
//			// ●4) 完成【兩步驟驗證】的所有要求如下:
//			// ●4-1) (請自行依照步驟要求操作之.....)
//
//			// ●5) 完成【應用程式密碼】的所有要求如下:
//			// ●5-1) 下拉式選單【選取應用程式】--> 選取【郵件】
//			// ●5-2) 下拉式選單【選取裝置】--> 選取【Windows 電腦】
//			// ●5-3) 最後按【產生】密碼
//			
////			final String myGmail = "ixlogic.wu@gmail.com";   // 寄出者的信箱
////			final String myGmail_password = "ddjomltcnypgcstn";
//			
//			final String myGmail = "ballbrotherx87@gmail.com";   // 寄出者的信箱
//			final String myGmail_password = "dqbyvnwyklogkkyl";
//
//			Session session = Session.getInstance(props, new Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(myGmail, myGmail_password);
//				}
//			});
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(myGmail));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//			// 設定信中的主旨
//			message.setSubject(subject);
//			// 設定信中的內容
//			message.setText(messageText);
//
//			Transport.send(message);
//			System.out.println("傳送成功!");
//		} catch (MessagingException e) {
//			System.out.println("傳送失敗!");
//			e.printStackTrace();
//		}
//	}
//
//	public static void main(String args[]) {
//
//		String to = "ballbrotherx87@gmail.com"; // 傳送的人的信箱
//
////		String subject = "密碼通知";
//
////		String ch_name = "peter1";
////		String passRandom = "111";
////		String messageText = "Hello! " + ch_name + " 請謹記此密碼: " + passRandom + "\n" + " (已經啟用)";
//
//		String subject = "做伙zuoheo密碼變更通知信 ! ";
//
//		String ch_name = "dear";
//		String passRandom = "1111";
//		String messageText = "Hello! " + ch_name + "我們將協助您重新設定一組新的密碼，請使用我們提供的一組密碼來進行登入。" +"\n"+ "*********請謹記此密碼********* " + "密碼:(" + passRandom + ")" + " (密碼已經啟用) 提醒您 成功登入後可以自行更改密碼喔!!!!";
//
//		MailService mailService = new MailService();
//		mailService.sendMail(to, subject, messageText);
//	}
//
//}

//package com.membership.model;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//import java.util.Properties;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeMessage;
//import java.util.Random;
//
//@Service
//public class MailService {
//
//	@Autowired
//	MembershipRepository repository;
//
//	public MailService() {
//		// 此為默認建構子，你可以根據需要添加其他建構子或初始化邏輯
//	}
//
//	// 設定傳送郵件:至收信人的Email信箱,Email主旨,Email內容
//	public void sendMail(String to, String subject) {
//		try {
//			// 產生包含英數字的8位密碼
//			String password = generateRandomVerificationCode();
//
//			// 設定使用SSL連線至 Gmail smtp Server
//			Properties props = new Properties();
//			props.put("mail.smtp.host", "smtp.gmail.com");
//			props.put("mail.smtp.socketFactory.port", "465");
//			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//			props.put("mail.smtp.auth", "true");
//			props.put("mail.smtp.port", "465");
//
//			final String myGmail = "ballbrotherx87@gmail.com"; // 寄出者的信箱
//			final String myGmail_password = "dqbyvnwyklogkkyl";
//
//			Session session = Session.getInstance(props, new Authenticator() {
//				protected PasswordAuthentication getPasswordAuthentication() {
//					return new PasswordAuthentication(myGmail, myGmail_password);
//				}
//			});
//
//			Message message = new MimeMessage(session);
//			message.setFrom(new InternetAddress(myGmail));
//			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//
//			// 設定信中的主旨
//			message.setSubject(subject);
//			// 設定信中的內容
//			String messageText = "Hello! 我們將協助您重新設定一組新的密碼，請使用我們提供的一組密碼來進行登入。\n" + "*********請謹記此密碼********* 密碼:("
//					+ password + ") (密碼已經啟用) 提醒您 成功登入後可以自行更改密碼喔!!!!";
//			message.setText(messageText);
//
//			Transport.send(message);
//			System.out.println("傳送成功!");
//		} catch (MessagingException e) {
//			System.out.println("傳送失敗!");
//			e.printStackTrace();
//		}
//	}
//
//	// 產生包含英數字的8位密碼
//	private String generateRandomVerificationCode() {
//		// 生成包含英數字的8位密碼
//		String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
//		StringBuilder verificationCode = new StringBuilder();
//		Random random = new Random();
//
//		for (int i = 0; i < 8; i++) {
//			int index = random.nextInt(characters.length());
//			verificationCode.append(characters.charAt(index));
//		}
//
//		return verificationCode.toString();
//	}
//}

package com.membership.service;

import org.springframework.stereotype.Service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


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
			final String myGmail_password = "dqbyvnwyklogkkyl"; // 寄出者密碼 

			Session session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(myGmail, myGmail_password);
				}
			});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(myGmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			// 設定信中的主旨
			message.setSubject(subject);

			String changePasswordLink = "http://localhost:8080/membership/updatepassword";

			// 設定信中的內容，使用傳入的驗證碼
			String messageText = "Hello! 我們將協助您重新設定一組新的密碼，請點擊以下連結進行更改密碼:\n" + changePasswordLink
					+ "\n*********請謹記此密碼********* 密碼:(" + verificationCode + ") (密碼已經啟用) 提醒您 成功登入後可以自行更改密碼喔!!!!";
			message.setText(messageText);

			Transport.send(message);
			System.out.println("傳送成功!");
		} catch (MessagingException e) {
			System.out.println("傳送失敗!");
			e.printStackTrace();
		}
	}

}
