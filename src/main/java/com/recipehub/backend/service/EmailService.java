package com.recipehub.backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Value("${app.backend.base-url}")
	private String backendBaseUrl;
	
	private final JavaMailSender mailSender;
	
	public EmailService(JavaMailSender mailSender)
	{
		this.mailSender = mailSender;
	}
	
	public void sendVerificationEmail(String to, String token)
	{
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(to);
		message.setSubject("Verify Your RecipeHub Account");
		message.setText("Please click the following link to verify your email : \n\n"
				+ backendBaseUrl
				+ "/api/users/verify?token="
				+ token
				);
		
		mailSender.send(message);
	}
}
