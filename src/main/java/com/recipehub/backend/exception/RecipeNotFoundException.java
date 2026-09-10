package com.recipehub.backend.exception;

public class RecipeNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUID = 1L;
	public RecipeNotFoundException(Long id)
	{
		super("Recipe with id " + id + " not found");
	}

}
