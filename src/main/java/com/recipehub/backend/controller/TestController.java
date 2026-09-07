package com.recipehub.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.repository.RecipeRepository;

@RestController
public class TestController {
	
	@Autowired
	private RecipeRepository recipeRepository;

	@GetMapping("/test")
	public String test()
	{
		return "backend working !!!";
	}
	
	@GetMapping("/testDB")
	public String testDB()
	{
		Recipe recipe = new Recipe();
		recipe.setTitle("Test Recipe");
		recipe.setCategory("Test Catagory");
		recipe.setDescription("Test Description");
		recipe.setLikes(0);
		recipeRepository.save(recipe);
		
		return "Recipe saved succefully !!" + recipeRepository.count();

	}
}
