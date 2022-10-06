package com.learnToday.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.learnToday.exception.ApiError;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.exception.ResourceAlreadyExistsException;


@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<ApiError> handleResourceException(ResourceNotFoundException exception){
		
		ApiError apiError = new ApiError(exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}
	
	@ExceptionHandler(value = ResourceAlreadyExistsException.class)
	public ResponseEntity<ApiError> handleResourceException(ResourceAlreadyExistsException exception){
		
		ApiError apiError = new ApiError(exception.getMessage());
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(apiError);
	}
	
}

