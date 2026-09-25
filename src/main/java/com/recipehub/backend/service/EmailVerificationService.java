package com.recipehub.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

import com.recipehub.backend.exception.InvalidTokenException;
import com.recipehub.backend.model.EmailVerificationToken;
import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.EmailVerificationTokenRepository;

@Service
public class EmailVerificationService {

	private final EmailVerificationTokenRepository tokenRepository;
	public EmailVerificationService(EmailVerificationTokenRepository tokenRepository)
	{
		this.tokenRepository = tokenRepository;
	}
	
	public EmailVerificationToken createToken(User user)
	{
		String token = UUID.randomUUID().toString();
		EmailVerificationToken verificationToken = new EmailVerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationToken.setExpiresAt(
		LocalDateTime.now().plusMinutes(15)		
		);	
		
		return tokenRepository.save(verificationToken);
	}
	
	@Transactional
	public void verifyToken(String token)
	{
		EmailVerificationToken verificationToken = tokenRepository.findByToken(token)
				.orElseThrow(() -> 
				new InvalidTokenException("Invalid Verification Token")
				);
		
		if(LocalDateTime.now().isAfter(verificationToken.getExpiresAt()))
		{
			throw new InvalidTokenException("Verification Token expired");
		}
		
		User user = verificationToken.getUser();
		user.setEmailVerified(true);
		tokenRepository.delete(verificationToken);	
	}
}