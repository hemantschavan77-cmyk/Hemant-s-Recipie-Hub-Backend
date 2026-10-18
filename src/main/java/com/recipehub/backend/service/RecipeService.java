package com.recipehub.backend.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.recipehub.backend.exception.RecipeNotFoundException;
import com.recipehub.backend.exception.UnauthorizedActionException;
import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.model.RecipeLike;
import com.recipehub.backend.model.RecipeSave;
import com.recipehub.backend.model.User;
import com.recipehub.backend.repository.RecipeLikeRepository;
import com.recipehub.backend.repository.RecipeRepository;
import com.recipehub.backend.repository.RecipeSaveRepository;
import com.recipehub.backend.repository.UserRepository;

@Service
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	
	private final CurrentUserService currentUserService;
	
	private final UserRepository userRepository;
	
	private final RecipeLikeRepository recipeLikeRepository;
	
	private final RecipeSaveRepository recipeSaveRepository;
	
	public RecipeService(RecipeRepository recipeRepository,
						 CurrentUserService currentUserService,
						 UserRepository userRepository,
						 RecipeLikeRepository recipeLikeRepository,
						 RecipeSaveRepository recipeSaveRepository
						)
	{
		this.recipeRepository = recipeRepository;
		this.currentUserService = currentUserService;
		this.userRepository = userRepository; 
		this.recipeLikeRepository = recipeLikeRepository;
		this.recipeSaveRepository = recipeSaveRepository;
	}
	
	public Page<Recipe> getAllRecipes(int page , int size)
	{
		Pageable pageable = PageRequest.of(page, size);
		return recipeRepository.findAllByOrderByCreatedAtDesc(pageable);
	}
	
	public Recipe addRecipe(Recipe recipe)
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
	
	@Transactional	
	public Recipe toggleLikes(Long id)
	{
		Recipe recipe = recipeRepository.findById(id).orElseThrow(()-> new RecipeNotFoundException(id));
		long currentUserId = currentUserService.getCurrentUserId();
	    Optional<RecipeLike> existingLike = recipeLikeRepository.findByUserIdAndRecipeId(currentUserId,id);
		if(existingLike.isPresent())
		{
			recipeLikeRepository.delete(existingLike.get());
			long likeCount = 	recipeLikeRepository.countByRecipeId(id);
			recipe.setLikes((int) likeCount);
			
		}
		else
		{
			User currentUser = userRepository.findById(currentUserId)
	                .orElseThrow(() -> new RuntimeException("User not found"));

	        RecipeLike recipeLike = new RecipeLike();
	        recipeLike.setUser(currentUser);
	        recipeLike.setRecipe(recipe);

	        recipeLikeRepository.save(recipeLike);

	        long likeCount = 	recipeLikeRepository.countByRecipeId(id);
			recipe.setLikes((int) likeCount);
		}
		
		return recipeRepository.save(recipe);
		
		
	}
	
	public List<Recipe> searchByTitle(String title)
	{
		return recipeRepository.findByTitleContainingIgnoreCase(title);
	}
	
	@Transactional
	public boolean toggleSavedRecipe(Long recipeId)
	{
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()-> new RecipeNotFoundException(recipeId));
		long currentUserId = currentUserService.getCurrentUserId();
		Optional<RecipeSave> existingSave = recipeSaveRepository.findByUserIdAndRecipeId(currentUserId, recipeId);
		if(existingSave.isPresent())
		{
			recipeSaveRepository.delete(existingSave.get());
			return false;
		}
		
		User user = userRepository.findById(currentUserId).orElseThrow(()-> new RuntimeException("User not found!!"));
		RecipeSave recipeSave = new RecipeSave();
		recipeSave.setUser(user);
		recipeSave.setRecipe(recipe);
		recipeSaveRepository.save(recipeSave);
		return true;
	}
	
	public List<Recipe> getSavedRecipes()
	{
		long currentUserId = currentUserService.getCurrentUserId();
		List<RecipeSave> savedRecipes = recipeSaveRepository.findByUserIdOrderByIdDesc(currentUserId);
		List<Recipe> recipes = new ArrayList<>();
		for(RecipeSave savedRecipe : savedRecipes)
		{
			Recipe recipe = savedRecipe.getRecipe();
			recipes.add(recipe);
			
		}
		
		return recipes;
	}
}



