package com.recipehub.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.recipehub.backend.model.Recipe;
import com.recipehub.backend.repository.RecipeRepository;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	
	public RecipeService(RecipeRepository recipeRepository)
	{
		this.recipeRepository = recipeRepository;
	}
	
	public List<Recipe> getAllRecipes()
	{
		return recipeRepository.findAll();
	}
	
	public Recipe saveRecipe(Recipe recipe)
	{
		recipe.setLikes(0);	
		return recipeRepository.save(recipe);
	}
	
	public Recipe getRecipeById(long id) {
	    return recipeRepository.findById(id).orElse(null);
	}
	
	public boolean deleteRecipeById(Long id)
	{
		if(recipeRepository.existsById(id))
		{
			recipeRepository.deleteById(id);
			return true;
		}
		return false;
	}
	
	public Recipe incrementLikes(Long id)
	{
		Recipe recipe = recipeRepository.findById(id).orElse(null);
		
		if(recipe == null)
		{
			return null;
		}
		
		recipe.setLikes(recipe.getLikes() + 1);
		return recipeRepository.save(recipe);
	}
	
	public List<Recipe> searchByTitle(String title)
	{
		return recipeRepository.findByTitleContainingIgnoreCase(title);
	}

}



