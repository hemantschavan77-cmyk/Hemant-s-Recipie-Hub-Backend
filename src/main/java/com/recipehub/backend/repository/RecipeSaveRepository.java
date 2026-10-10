package com.recipehub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.RecipeSave;

public interface RecipeSaveRepository extends JpaRepository<RecipeSave, Long> {

	boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
}
