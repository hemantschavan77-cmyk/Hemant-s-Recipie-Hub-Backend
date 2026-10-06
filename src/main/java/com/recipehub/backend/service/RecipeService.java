package com.recipehub.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import com.recipehub.backend.exception.RecipeNotFoundException;
import com.recipehub.backend.exception.UnauthorizedActionException;
import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.RecipeRepository;
import com.recipehub.backend.repository.UserRepository;

@Service
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	
	private final CurrentUserService currentUserService;
	
	private final UserRepository userRepository;
	
	public RecipeService(RecipeRepository recipeRepository,
						 CurrentUserService currentUserService,
						 UserRepository userRepository)
	{
		this.recipeRepository = recipeRepository;
		this.currentUserService = currentUserService;
		this.userRepository = userRepository; 
	}
	
	public List<Recipe> getAllRecipes()
	{
		return recipeRepository.findAll();
	}
	
	public Recipe saveRecipe(Recipe recipe)
	{
		long currentUserId = currentUserService.getCurrentUserId();
		
		User currentUser = userRepository.findById(currentUserId)
				.orElseThrow(()-> new RuntimeException("User not found"));
		
		recipe.setUser(currentUser);
		recipe.setLikes(0);
		recipe.setCreatedAt(LocalDateTime.now());
		return recipeRepository.save(recipe);
	}
	
	public Recipe getRecipeById(long id) {
	    return recipeRepository.findById(id).orElseThrow(()->new RecipeNotFoundException(id));
	}
	
	public void deleteRecipeById(Long id)
	{
		Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException(id));
		long currentUserId = currentUserService.getCurrentUserId();
		long recipeOwnerId = recipe.getUser().getId();
		
		if(currentUserId != recipeOwnerId)
		{
			throw new UnauthorizedActionException("You are not allowed to delete this recipe");
		}
		
		recipeRepository.delete(recipe);
	}
	
	public Recipe incrementLikes(Long id)
	{
		Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException(id));
		
		recipe.setLikes(recipe.getLikes() + 1);
		return recipeRepository.save(recipe);
	}
	
	public List<Recipe> searchByTitle(String title)
	{
		return recipeRepository.findByTitleContainingIgnoreCase(title);
	}

}



