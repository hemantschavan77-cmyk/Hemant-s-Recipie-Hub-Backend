package com.recipehub.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.recipehub.backend.security.JwtAuthenticationFilter;
import com.recipehub.backend.service.JwtService;

@Configuration
public class SecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception{
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
					.requestMatchers(
							"/api/recipes", 
							"/api/recipes/**",
							"/api/users/register", 
							"/api/users/verify",
							"/api/users/login"
							).permitAll()
					.anyRequest().authenticated()
			).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}	
	
	@Bean
	PasswordEncoder passwordencoder() 
	{
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService)
	{
		return new JwtAuthenticationFilter(jwtService);
	}	
	
}
