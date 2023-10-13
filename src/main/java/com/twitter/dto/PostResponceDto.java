package com.twitter.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponceDto {
	
	private String title;
	private String content;
	private String description;
	private Date addDate;
	private UserDto user;
	private List<PostDto> postListDto;
}
