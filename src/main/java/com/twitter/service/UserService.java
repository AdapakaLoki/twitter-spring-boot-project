package com.twitter.service;

import java.util.List;

import com.twitter.dto.LoginDto;
import com.twitter.dto.RequestUserUpdateDto;
import com.twitter.dto.UserDto;

public interface UserService {
	
	public UserDto saveUser(UserDto userDto);
	
	public UserDto getUserByEmail(String email);
	
	public RequestUserUpdateDto updateUserById(Long id,RequestUserUpdateDto userDto);
	
	public void deleteUserById(Long id);
	
	public List<UserDto> getAllUsers();
	
	public String logInUser(LoginDto loginDto);
	
}
