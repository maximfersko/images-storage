package com.fersko.storage.controller;

import com.fersko.storage.exceptions.AuthenticateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerController {
	@ExceptionHandler(AuthenticateException.class)
	public ResponseEntity<String> handleAuthenticateException(AuthenticateException e) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
}
