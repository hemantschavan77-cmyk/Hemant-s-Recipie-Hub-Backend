package com.recipehub.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.RecipeLike;

public interface RecipeLikeRepository extends JpaRepository<RecipeLike , Long> {
	Optional<RecipeLike> findByUserIdAndRecipeId(Long userId, Long recipeId);
}
