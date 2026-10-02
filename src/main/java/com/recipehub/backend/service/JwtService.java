package com.recipehub.backend.service;


import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private final SecretKey secretKey;
	
	private final long expiration;
	
	public JwtService(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration}") long expiration)
	{
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		this.expiration = expiration;
	}
	
	public String generateToken(long userId, String email)
	{
		Date now = new Date();
		Date expiry = new Date(now.getTime() + expiration);
		
		return Jwts.builder()
				.subject(String.valueOf(userId))
				.claim("email", email)
				.issuedAt(now)
				.expiration(expiry)
				.signWith(secretKey)
				.compact();
	}
	
	public long extractUserId(String token)
	{
		Jws<Claims> claims = Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token);
		
		return Long.parseLong(claims.getPayload().getSubject());
	}
}
