package com.twitter.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IllegalargumentExceptions extends RuntimeException{
	private String defaultMessage;
    private String fieldName;
   
	
}
