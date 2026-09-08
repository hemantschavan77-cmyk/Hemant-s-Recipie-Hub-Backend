package com.recipehub.backend.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.recipehub.backend.exception.ErrorResponse;
import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.service.RecipeService;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "http://localhost:3000")
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
		recipe.setLikes(0);
		return recipeService.saveRecipe(recipe);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getRecipeById(@PathVariable Long id)
	{
		Recipe recipe = recipeService.getRecipeById(id);
		
		if(recipe == null)
		{
			ErrorResponse error = new ErrorResponse("Recipe with Id " + id +" is not found ");
			return ResponseEntity.status(404).body(error);
			
		}
		return ResponseEntity.ok(recipe);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRecipeById(@PathVariable long id)
	{
		boolean deleted = recipeService.deleteRecipeById(id);
		
		if(!deleted)
		{
			ErrorResponse error = new ErrorResponse("Recipe with "+ id+" is not found");
			return ResponseEntity.status(404).body(error);
		}
		return ResponseEntity.ok().body("Recipe Deleted successfully");
	}
	
	@PatchMapping("/{id}/like")
	public ResponseEntity<?> likeRecipe(@PathVariable Long id)
	{
		Recipe updatedRecipe = recipeService.incrementLikes(id);
		if(updatedRecipe == null)
		{
			ErrorResponse error = new ErrorResponse("Recipe with id "+ id +"is not found");
			return ResponseEntity.status(404).body(error);
		}
		
		return ResponseEntity.ok(updatedRecipe);
	}
	
	@GetMapping("/search")
	public List<Recipe> searchRecipe(@RequestParam String title)
	{
		return recipeService.searchByTitle(title);
	}
}








