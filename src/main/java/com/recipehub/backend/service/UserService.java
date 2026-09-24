package com.recipehub.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recipehub.backend.model.EmailVerificationToken;
import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;	
	
	private final EmailVerificationService emailVerificationService;
	
	private final EmailService emailService;
	public UserService(UserRepository userRepository
						, PasswordEncoder passwordEncoder
						, EmailVerificationService emailVerificationService
						,EmailService emailService)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationService = emailVerificationService;
		this.emailService = emailService;
	}
	
	public Optional<User> findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
	
	public User registerUser(User user)
	{
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setEmailVerified(false);
		User savedUser = userRepository.save(user);
		EmailVerificationToken token = emailVerificationService.createToken(savedUser);
		emailService.sendVerificationEmail(savedUser.getEmail(), token.getToken());
		return savedUser;
	}
}
