package com.twitter.service;

import com.twitter.dto.CommentDto;

public interface CommentService {
	
	public CommentDto createComment(Long postId,CommentDto commentDto);
	
	public void deleteComment(Long commentId);
}
