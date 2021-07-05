package com.egeroo.roocvthree.core.util;

import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;
public class send_email_gmail {
	public static void main(String[] args) {
		final String username = "username";
		final String password = "password.";
		System.out.print(username);
		System.out.println("Connecting...");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");//smtp.gmail.com
		props.put("mail.smtp.port", "25");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
			}
		  });
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("email@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
			InternetAddress.parse("email@server.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler holla from generali,"
				+ "\n\n No spam to my email, please!"
				+ "Visit https://generali.co.id");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			System.out.println("failed");
			throw new RuntimeException(e);
		}
	}
}
