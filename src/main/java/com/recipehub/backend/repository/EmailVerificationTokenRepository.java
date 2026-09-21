package com.recipehub.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.EmailVerificationToken;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken , Long> {

	
	Optional<EmailVerificationToken> findByToken(String token);
}
