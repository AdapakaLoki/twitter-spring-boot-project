package com.twitter.serviceimpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twitter.constents.Constants;
import com.twitter.dto.CommentDto;
import com.twitter.entity.Comment;
import com.twitter.entity.Post;
import com.twitter.exceptions.IllegalargumentExceptions;
import com.twitter.repo.CommentRepository;
import com.twitter.repo.PostRepository;
import com.twitter.service.CommentService;
@Service
public class CommentServiceImpl implements CommentService{
	
	private static final Logger logger=LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public CommentDto createComment(Long postId, CommentDto commentDto) {
		logger.debug("this is createComment method");
		Post post=postRepository.findById(postId).orElseThrow(()->new IllegalargumentExceptions(Constants.POST_ID_NOTFOUND, "postid"+postId));
		logger.error("post  not found in createComment method");
		Comment comment=this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment saveComment=commentRepository.save(comment);
		logger.info("comment save successfully");
		return this.modelMapper.map(saveComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		logger.debug("this is deleteComment method");
		Comment comment=commentRepository.findById(commentId).orElseThrow(()->new IllegalargumentExceptions(Constants.COMMENT_ID_NOTFOUND, "commentId"+commentId));
		logger.error("comment  not found in deleteComment method");
		commentRepository.delete(comment);
	}

}
