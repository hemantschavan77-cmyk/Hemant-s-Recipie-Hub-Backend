package com.recipehub.backend.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.recipehub.backend.service.JwtService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtService jwtService;
	
	public JwtAuthenticationFilter(JwtService jwtService)
	{
		this.jwtService = jwtService;
	}
	
	@Override
	public void doFilterInternal(HttpServletRequest request,
								 HttpServletResponse response, 
								 FilterChain filterChain) 
								 throws ServletException, IOException
	{
		String authorizationHeader  = request.getHeader("Authorization");
		
		if(authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authorizationHeader.substring(7);
		try {
			long userId = jwtService.extractUserId(token);
			
			UsernamePasswordAuthenticationToken authentication= new UsernamePasswordAuthenticationToken(
					userId,
					null,
					Collections.emptyList()
					);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch(JwtException | IllegalArgumentException ex)
		{
			SecurityContextHolder.clearContext();
		}
		filterChain.doFilter(request, response);
	}
}
