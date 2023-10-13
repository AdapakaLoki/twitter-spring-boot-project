package com.twitter.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{
	
}
