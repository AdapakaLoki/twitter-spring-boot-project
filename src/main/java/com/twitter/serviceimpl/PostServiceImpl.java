package com.twitter.serviceimpl;



import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.twitter.constents.Constants;
import com.twitter.dto.PostDto;
import com.twitter.dto.PostResponce;
import com.twitter.entity.Comment;
import com.twitter.entity.Post;
import com.twitter.entity.User;
import com.twitter.exceptions.IllegalargumentExceptions;
import com.twitter.repo.PostRepository;
import com.twitter.repo.UserRepository;
import com.twitter.service.PostService;
@Service
public class PostServiceImpl implements PostService{
	private static final Logger logger=LoggerFactory.getLogger(PostServiceImpl.class);

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public PostDto createPost(Long userId, PostDto postDto) {
		logger.debug("this is createPost method");
		User user=userRepository.findById(userId).orElseThrow(()->new IllegalargumentExceptions(Constants.USER_ID_NOTFOUND,"userId"+userId));
		logger.error("user  not found in postcreate method");
		Post post=mapToEntity(postDto);
		post.setAddDate(new Date());
		post.setUser(user);
		Post savePost=postRepository.save(post);
		logger.info("post save successfully");
		postDto=mapTODto(savePost);
		return postDto;
	}
	
	private PostDto mapTODto(Post post) {
		return this.modelMapper.map(post, PostDto.class);
	}
	
	private Post mapToEntity(PostDto postDto) {
		return this.modelMapper.map(postDto, Post.class);
	}

	@Override
	public PostDto updatePostById(Long postId, PostDto postDto) {
		logger.debug("this is updatePost method");
		Post post=postRepository.findById(postId).orElseThrow(()->new IllegalargumentExceptions(Constants.POST_ID_NOTFOUND, "postid"+postId));
		logger.error("post  not found in postUpdate method");
		if(postDto.getTitle()!=null) 
			post.setTitle(postDto.getTitle());
		if(postDto.getContent()!=null)
			post.setContent(postDto.getContent());
		if(postDto.getDescription()!=null)
			post.setDescription(postDto.getDescription());
		post.setAddDate(new Date());
		Post savePost=postRepository.save(post);
		logger.info("post update successfully");
		return mapTODto(savePost);
	}

	@Override
	public PostDto getPostById(Long postId) {
		logger.debug("this is getPostById method");
		Post post=postRepository.findById(postId).orElseThrow(()->new IllegalargumentExceptions(Constants.POST_ID_NOTFOUND, "postid"+postId));
		logger.error("post  not found in getPostByid method");
		Set<Comment> com= new HashSet<>();
		post.setComment(com);
		PostDto pDto=this.modelMapper.map(post, PostDto.class);
		logger.info("post  getById successfully");
		return pDto;
	}

	@Override
	public List<PostDto> getAllPostsByuserId(Long userId) {
		logger.debug("this is getAllPostsByUserId method");
		User user=userRepository.findById(userId).orElseThrow(()->new IllegalargumentExceptions(Constants.USER_ID_NOTFOUND,"userId"+userId));
		logger.error("user  not found in postcreate method");
		List<Post> posts=postRepository.findByUser(user);
		List<PostDto> postList=posts.stream().map(post -> mapTODto(post)).collect(Collectors.toList());
		logger.info("user save successfully");
		return postList;
	}

	@Override
	public void deletePostById(Long postId) {
		logger.debug("this is deletePostById method");
		postRepository.findById(postId).orElseThrow(()->new IllegalargumentExceptions(Constants.POST_ID_NOTFOUND, "postid"+postId));
		logger.error("post  not found in getPostByid method");
		postRepository.deleteById(postId);
		logger.info("delete post successfully");
	}

	@Override
	public PostResponce getAllPosts(Integer pagenumber,Integer pageSize,String sortBy,String sortDr) {
		logger.debug("this is getAllPosts method");
		Sort sort=(sortDr.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending());
		Pageable p = PageRequest.of(pagenumber, pageSize,sort);
		Page<Post> posts=postRepository.findAll(p);
		List<Post> postList=posts.getContent();
		List<PostDto> postDtoList=postList.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		logger.info("getAllPosts successfully");
		PostResponce responce = new PostResponce();
		responce.setContent(postDtoList);
		responce.setPageNumber(posts.getNumber());
		responce.setPageSize(posts.getSize());
		responce.setTotalElements(posts.getNumberOfElements());
		responce.setTotalPages(posts.getTotalPages());
		responce.setLastPage(posts.isLast());
		return responce;
	}
	
	
}
