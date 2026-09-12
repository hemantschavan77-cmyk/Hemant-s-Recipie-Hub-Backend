package com.recipehub.backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

   private final RecipeService recipeService;
   public RecipeController(RecipeService recipeService)
   {
	this.recipeService = recipeService;   
   }
   
	@GetMapping
	public List<Recipe> getAllRecipes()
	{
		return recipeService.getAllRecipes();
	}
	
	@PostMapping
	public Recipe saveRecipe(@RequestBody Recipe recipe)
	{
		return recipeService.saveRecipe(recipe);
	}
	
	@GetMapping("/{id}")
	public Recipe getRecipeById(@PathVariable Long id)
	{
		return recipeService.getRecipeById(id);
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteRecipeById(@PathVariable long id)
	{
		recipeService.deleteRecipeById(id);
		return ResponseEntity.ok("Recipe Deleted successfully");
	}
	
	@PatchMapping("/{id}/like")
	public Recipe likeRecipe(@PathVariable Long id)
	{
		return recipeService.incrementLikes(id);
	}
	
	@GetMapping("/search")
	public List<Recipe> searchRecipe(@RequestParam String title)
	{
		return recipeService.searchByTitle(title);
	}
}








