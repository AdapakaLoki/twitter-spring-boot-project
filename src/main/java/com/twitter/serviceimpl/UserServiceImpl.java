package com.twitter.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.twitter.constents.Constants;
import com.twitter.dto.LoginDto;
import com.twitter.dto.RequestUserUpdateDto;
import com.twitter.dto.UserDto;
import com.twitter.entity.Role;
import com.twitter.entity.User;
import com.twitter.exceptions.IllegalargumentExceptions;
import com.twitter.repo.UserRepository;
import com.twitter.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDto saveUser(UserDto userDto) {
		logger.debug("this is user save method");
		User user = null;
		if (userRepository.findByEmailOrMobileNo(userDto.getEmail(), userDto.getMobileNo()).isPresent()) {
			logger.error("email or mobile not found");
			throw new IllegalargumentExceptions(Constants.EMAIL_OR_MOBILENUMBER, "email or mobileno");
		}
		user = mapToEntity(userDto);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setRoleName("ROLE_USER");
		roles.add(role);
		user.setRoles(roles);
		User saveUser = userRepository.save(user);
		logger.info("user save successfully");
		userDto = mapToDto(saveUser);
		return userDto;

	}

	@Override
	public UserDto getUserByEmail(String email) {
		logger.debug("this is getUserByEmail method");
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalargumentExceptions(Constants.EMAIL_NOT_FOUND, email));
		logger.error("email  not found");
		UserDto userDto = mapToDto(user);
		return userDto;
	}

	@Override
	public RequestUserUpdateDto updateUserById(Long id,RequestUserUpdateDto userDto) {
		logger.debug("this is getUserByEmail method");
		User user = userRepository.findById(id)
				.orElseThrow(() -> new IllegalargumentExceptions(Constants.ID_NOT_FOUND, "id"+id));
		logger.error("userId not found");
		if (userDto.getFirstName() != null) {
			user.setFirstName(userDto.getFirstName());
		}
		if (userDto.getLastName() != null) {
			user.setLastName(userDto.getLastName());
		}
		if (userDto.getMobileNo() != null) {
			user.setMobileNo(userDto.getMobileNo());
		}
		if (userDto.getEmail() != null) {
			user.setEmail(userDto.getEmail());
			}
		if (userDto.getPassword() != null) {
			user.setPassword(userDto.getPassword());
		}
		User upDateUser=userRepository.save(user);
		logger.info("user update successfully");
		return mapToUpdateDto(upDateUser);
	}

	@Override
	public void deleteUserById(Long id) {
		User user=userRepository.findById(id).orElseThrow(()->new IllegalargumentExceptions(Constants.EMAIL_NOT_FOUND,"id"+id));
		userRepository.deleteById(user.getId());
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> userList = userRepository.findAll();
		List<UserDto> userDtoList = userList.stream().map(users -> mapToDto(users)).collect(Collectors.toList());
		return userDtoList;
	}

	private UserDto mapToDto(User user) {
		return this.modelMapper.map(user, UserDto.class);
	}

	private User mapToEntity(UserDto userDto) {
		return this.modelMapper.map(userDto, User.class);
	}
	private RequestUserUpdateDto mapToUpdateDto(User user) {
		return this.modelMapper.map(user, RequestUserUpdateDto.class);
	}

	@Override
	public String logInUser(LoginDto loginDto) {
		logger.debug("now we are in loginuser method");
		logger.trace("user authentication");
		Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword()));
		
		if(!authentication.isAuthenticated()) { 
			logger.error("user not fount");
			throw new BadCredentialsException(Constants.INVALID_CREDIENTIALS);
		}
		logger.warn("we are testing with spring boot application");
			SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info("user login successfully");
		return "user login successfull";
	}

}
