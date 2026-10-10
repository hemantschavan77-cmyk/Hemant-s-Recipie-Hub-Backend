package com.recipehub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.RecipeSave;
import java.util.List;


public interface RecipeSaveRepository extends JpaRepository<RecipeSave, Long> {

	boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
	
	void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
	
	List<RecipeSave> findByUserId(Long userId);
	
}
