package com.recipehub.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recipehub.backend.model.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	List<Recipe> findByTitleContainingIgnoreCase(String title);


}
