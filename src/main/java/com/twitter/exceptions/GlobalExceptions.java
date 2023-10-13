package com.twitter.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.twitter.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptions {
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> methodNotvalidException(MethodArgumentNotValidException mException){
		Map<String, String> errorMap = new HashMap<>();
		mException.getFieldErrors().forEach(error -> {
			String fError=error.getField();
			String dMessage=error.getDefaultMessage();
			errorMap.put(fError, dMessage);
		});
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(IllegalargumentExceptions.class)
	public ResponseEntity<ErrorDetails> illegalFieldExceptions(IllegalargumentExceptions exception,WebRequest request){
		ErrorDetails er = new ErrorDetails(new Date(), exception.getDefaultMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorDetails> illegalFieldExceptions(BadCredentialsException exception,WebRequest request){
		ErrorDetails er = new ErrorDetails(new Date(), exception.getMessage()
				, request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(er,HttpStatus.BAD_REQUEST);
	}
}
