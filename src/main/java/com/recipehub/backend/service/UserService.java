package com.recipehub.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;	
	
	private final EmailVerificationService emailVerificationService;
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailVerificationService emailVerificationService)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationService = emailVerificationService;
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
		emailVerificationService.createToken(savedUser);
		return savedUser;
	}
}
