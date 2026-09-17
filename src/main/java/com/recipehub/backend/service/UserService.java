package com.recipehub.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository)
	{
		this.userRepository = userRepository;
	}
	
	public Optional<User> findByEmail(String email)
	{
		return userRepository.findByEmail(email);
	}
}
