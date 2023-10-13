package com.twitter.dto;

import lombok.Data;

@Data
public class SearchRequestDto {
	private String column;
	private String value;
	private Operation operation;
	public enum Operation{
		EQUAL,LIKE,IN,GREATER_THAN,LESS_THAN,BETWEEN;
	}
}
