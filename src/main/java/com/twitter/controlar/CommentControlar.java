package com.twitter.controlar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.CommentDto;
import com.twitter.service.CommentService;

@RestController
@RequestMapping("/api/v3")
public class CommentControlar {
	private static final Logger logger=LoggerFactory.getLogger(CommentControlar.class);

	@Autowired
	public CommentService commentService;
	
	@PostMapping("/post/{postId}/comment")
	public ResponseEntity<CommentDto> createCommet(@PathVariable(value = "postId")Long postId,@RequestBody CommentDto commentDto){
		logger.debug("this is createCommet method in comment controlar");
		return new ResponseEntity<CommentDto>(commentService.createComment(postId, commentDto),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable(value = "commentId")Long commentId){
		logger.debug("this is deleteComment method in comment controlar");
		commentService.deleteComment(commentId);
		return new ResponseEntity<String>("comment delete successfull",HttpStatus.OK);
	}
}
