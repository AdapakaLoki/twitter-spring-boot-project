package com.twitter.service;

import java.util.List;

import com.twitter.dto.PostDto;
import com.twitter.dto.PostResponce;

public interface PostService {
	
	public PostDto createPost(Long userId,PostDto postDto);
	
	public PostDto updatePostById(Long postId,PostDto postDto);
	
	public PostDto getPostById(Long postId);
	
	public List<PostDto> getAllPostsByuserId(Long userId);
	
	public void deletePostById(Long postId);
	
	public PostResponce getAllPosts(Integer pagenumber,Integer pageSize,String sortBy,String sortDr);
}
