package com.recipehub.backend.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	Page<Recipe> findByTitleContainingIgnoreCase(String title,Pageable pageable);
	
	Page<Recipe> findAllByOrderByCreatedAtDesc(Pageable pageable);
	
	Page<Recipe> findAllByOrderByLikesDesc(Pageable pageable);
	
	List<Recipe> findByUserIdOrderByCreatedAtDesc(Long userId);
	
	Page<Recipe> findByCategoryIgnoreCase(String category, Pageable pageable);
	
	Page<Recipe> findByTitleContainingIgnoreCaseAndCategoryIgnoreCase(
	        String title,
	        String category,
	        Pageable pageable
	);
}
	