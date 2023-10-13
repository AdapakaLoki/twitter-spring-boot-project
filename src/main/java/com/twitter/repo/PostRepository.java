package com.twitter.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.twitter.entity.Post;
import com.twitter.entity.User;

public interface PostRepository extends JpaRepository<Post, Long>{
	List<Post> findByUser(User user);
}
