package com.recipehub.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipehub.backend.dto.AuthResponse;
import com.recipehub.backend.dto.LoginRequest;
import com.recipehub.backend.model.User;
import com.recipehub.backend.service.EmailVerificationService;
import com.recipehub.backend.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {
	
	private final UserService userService;
	
	private final EmailVerificationService emailVerificationService;
	
	public UserController(UserService userService ,EmailVerificationService emailVerificationService)
	{
		this.userService = userService;
		this.emailVerificationService = emailVerificationService;
	}
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user)
	{
		return userService.registerUser(user);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request)
	{
		return userService.login(request);
	}
	
	@GetMapping("/verify")
	public String verifyEmail(@RequestParam String token)
	{
		emailVerificationService.verifyToken(token);
		return "Email Verified successfully !!!";
	}
	

}
