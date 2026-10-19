package com.recipehub.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.RecipeSave;
import java.util.List;
import java.util.Optional;


public interface RecipeSaveRepository extends JpaRepository<RecipeSave, Long> {

	boolean existsByUserIdAndRecipeId(Long userId, Long recipeId);
	
	void deleteByUserIdAndRecipeId(Long userId, Long recipeId);
	
	List<RecipeSave> findByUserIdOrderByIdDesc(Long userId);
	
	Optional<RecipeSave> findByUserIdAndRecipeId(Long userId, Long recipeId);
	
	void deleteByRecipeId(Long recipeId);
	
}
