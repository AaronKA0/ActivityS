package com.venorder.service;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class BookingMail {
    
    public void sendMail(String to, String subject, String messageText) {

        try {
            // 設定使用SSL連線至 Gmail smtp Server
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");

            final String myGmail = "aaron38123@gmail.com";
            final String myGmail_password = "fpagjhmhfsgnhavq";
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
            // 設定信中的內容
            message.setText(messageText);

            Transport.send(message);
            System.out.println("傳送成功!");
        } catch (MessagingException e) {
            System.out.println("傳送失敗!");
            e.printStackTrace();
        }
    }
    
    
//    public static String generateUrl() {
//        String uniqueId = UUID.randomUUID().toString();
//        String url = "https://example.com/" + uniqueId;
//        return url;
//    }


//    public static void main(String args[]) {
//
//        String to = "aaron38123@gmail.com";
//
//        String subject = "租借場地滿意度通知";
//
//        String ch_name = "peter1";
//        String passRandom = "111";
//        String messageText = "Hello! " + ch_name + " : 感謝您租借我們的場地，請我們填寫使用滿意度!!";
//
//        BookingMail bookingMail = new BookingMail();
//        bookingMail.sendMail(to, subject, messageText);
//    }
    
}
