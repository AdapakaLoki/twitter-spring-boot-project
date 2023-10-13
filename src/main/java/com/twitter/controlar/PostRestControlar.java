package com.twitter.controlar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.ApiResponce;
import com.twitter.dto.PostDto;
import com.twitter.dto.PostResponce;
import com.twitter.service.PostService;

@RestController
@RequestMapping("/api/v2")
public class PostRestControlar {
	private static final Logger logger=LoggerFactory.getLogger(PostRestControlar.class);
	@Autowired
	private PostService postService;
	
	@PostMapping("/user/{userId}/post")
	public ResponseEntity<?> createPost(@PathVariable(value = "userId") Long userId,@RequestBody PostDto postDto){
		logger.debug("this is createPost method in post controlar");
		postDto=postService.createPost(userId, postDto);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.CREATED);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> upDatePostById(@PathVariable(value = "postId")Long postId,@RequestBody PostDto postDto){
		logger.debug("this is upDatePostById method in post controlar");
		return new ResponseEntity<PostDto>(postService.updatePostById(postId, postDto),HttpStatus.OK);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostsById(@PathVariable(value = "postId")Long postId){
		logger.debug("this is getPostsById method in post controlar");
		return new ResponseEntity<PostDto>(postService.getPostById(postId),HttpStatus.OK);
	}
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getAllPostsByUserId(@PathVariable(value = "userId")Long userId){
		logger.debug("this is getAllPostsByUserId method in post controlar");
		return new ResponseEntity<List<PostDto>>(postService.getAllPostsByuserId(userId),HttpStatus.OK);
	}
	
	@DeleteMapping("/post/{postId}")
	public ApiResponce deletePostById(@PathVariable(value = "postId")Long postId){
		logger.debug("this is deletePostById method in post controlar");
		postService.deletePostById(postId);
		return new ApiResponce("post delete successfull", true);
	}
	
	@GetMapping("/post")
	public ResponseEntity<PostResponce> getAllPosts(@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "2",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
			@RequestParam(value = "sortDr",defaultValue = "asc",required = false)String sortDr){
		PostResponce allPosts = postService.getAllPosts(pageNumber,pageSize,sortBy,sortDr);
		logger.debug("this is getAllPosts method in post controlar");
		return new ResponseEntity<PostResponce>(allPosts,HttpStatus.OK);
	}
	
}
