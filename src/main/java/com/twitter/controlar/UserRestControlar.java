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
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.LoginDto;
import com.twitter.dto.RequestUserUpdateDto;
import com.twitter.dto.UserDto;
import com.twitter.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserRestControlar {
	
	private static final Logger logger=LoggerFactory.getLogger(UserRestControlar.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		logger.debug("this is createUser method in user controlar");
		userDto=userService.saveUser(userDto);
		return new ResponseEntity<UserDto>(userDto,HttpStatus.CREATED);
	}
	@PostMapping("/login")
	public ResponseEntity<?> userLogIn(@RequestBody LoginDto loginDto){
		logger.debug("this is userLogIn method in user controlar");
		String message=userService.logInUser(loginDto);
		return new ResponseEntity<String>(message,HttpStatus.OK);
	}
	@GetMapping("/user")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		logger.debug("this is getAllUsers method in user controlar");
		List<UserDto> usersList=userService.getAllUsers();
		return new ResponseEntity<List<UserDto>>(usersList,HttpStatus.OK);
	}
	@GetMapping("/user/email")
	public ResponseEntity<UserDto> getUserByEmailId(@RequestBody UserDto userDto){
		logger.debug("this is getUserByEmailId method in user controlar");
		userDto=userService.getUserByEmail(userDto.getEmail());
		return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
	}
	@PutMapping("/user/{id}")
	public ResponseEntity<RequestUserUpdateDto> upDateUserById(@PathVariable(value = "id")Long id,@Valid @RequestBody RequestUserUpdateDto userDto){
		logger.debug("this is upDateUserById method in user controlar");
		userDto=userService.updateUserById(id, userDto);
		return new ResponseEntity<RequestUserUpdateDto>(userDto,HttpStatus.CREATED);
	}
	@DeleteMapping("/user/{id}")
	public ResponseEntity<?> deleteByEmail(@PathVariable(value = "id") Long id){
		logger.debug("this is deleteByEmail method in user controlar");
		userService.deleteUserById(id);
		return new ResponseEntity<String>("user delete successful",HttpStatus.OK);
	}
	
}
