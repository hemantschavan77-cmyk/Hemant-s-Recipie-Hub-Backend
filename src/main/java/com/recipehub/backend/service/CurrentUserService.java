package com.recipehub.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {
	public long getCurrentUserId()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return (Long) authentication.getPrincipal();
	}
}
