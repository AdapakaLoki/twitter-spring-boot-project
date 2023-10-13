package com.twitter.serviceimpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.twitter.constents.Constants;
import com.twitter.entity.User;
import com.twitter.exceptions.IllegalargumentExceptions;
import com.twitter.repo.UserRepository;

@Service
public class CustomeUserDetailsService implements UserDetailsService{
	
	private static final Logger logger=LoggerFactory.getLogger(CustomeUserDetailsService.class);

	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		logger.debug("this is loadUserByUsername method");
		User user=userRepository.findByEmail(email)
				.orElseThrow(()-> new IllegalargumentExceptions(Constants.EMAIL_NOT_FOUND, email));
		logger.error("user  not found in loadUserByUsername method");
		Set<GrantedAuthority> authorities=user.getRoles()
												.stream()
												.map(role->new SimpleGrantedAuthority(role.getRoleName()))
												.collect(Collectors.toSet());
		logger.info("user object set in springboot user ");
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
	
	}

}
