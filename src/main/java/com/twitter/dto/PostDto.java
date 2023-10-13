package com.twitter.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	
	private String title;
	private String content;
	private String description;
	private Date addDate;
	private UserDto user;
	private Set<CommentDto> comment=new HashSet<>();; 
	
}
