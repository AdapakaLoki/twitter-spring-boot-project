package com.twitter.dto;

import java.util.List;

import lombok.Data;

@Data
public class SpecificRequestDto {
	
	private List<SearchRequestDto> searchRequestDto;
	
	private GlobalOperator globalOperator;
	
	public enum GlobalOperator{
		AND,OR;
	}
}
