package com.recipehub.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecipeNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleRecipeNotFound(RecipeNotFoundException ex) {
		ErrorResponse error = new ErrorResponse(ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}