package com.recipehub.backend.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.recipehub.backend.dto.AuthResponse;
import com.recipehub.backend.dto.LoginRequest;
import com.recipehub.backend.exception.InvalidCredentialsException;
import com.recipehub.backend.model.EmailVerificationToken;
import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;	
	
	private final EmailVerificationService emailVerificationService;
	
	private final EmailService emailService;
	
	private final JwtService jwtService;
	
	public UserService(UserRepository userRepository
						, PasswordEncoder passwordEncoder
						, EmailVerificationService emailVerificationService
						,EmailService emailService
						,JwtService jwtService)
	{
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.emailVerificationService = emailVerificationService;
		this.emailService = emailService;
		this.jwtService = jwtService;
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
	
	public AuthResponse login(LoginRequest request)
	{
		User user = userRepository.findByEmail(request.getEmail())
				.orElseThrow(()-> 
					new InvalidCredentialsException("Invalid Email or Password"));
		
		if(!user.isEmailVerified())
		{
			throw new InvalidCredentialsException("Verify your email before logging in");
		}
		
		if(!passwordEncoder.matches(request.getPassword(),user.getPassword()))
		{
			throw new InvalidCredentialsException("Invalid Email or Password");

		}
		
		String token = jwtService.generateToken(user.getId(), user.getEmail());
		
		return new AuthResponse(token);
	}
}
