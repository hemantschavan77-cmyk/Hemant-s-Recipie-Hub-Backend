package com.recipehub.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.User;

public interface UserRepository extends JpaRepository<User ,Long> {

	Optional<User> findByEmail(String email);
}
