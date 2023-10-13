package com.twitter.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.twitter.entity.User;

public interface UserRepository extends JpaRepository<User, Long>,JpaSpecificationExecutor<User>{
	Optional<User> findByEmail(String email);
	Optional<User> findByEmailOrMobileNo(String email,String mobileNo);
		
}
