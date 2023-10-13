package com.twitter.controlar;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.SpecificRequestDto;
import com.twitter.dto.UserDto;
import com.twitter.entity.User;
import com.twitter.repo.UserRepository;
import com.twitter.serviceimpl.SpecificSearchService;

@RestController
@RequestMapping("/api/v4")
public class SpecificationControler {
	
	@Autowired
	private SpecificSearchService<User> specificSearchService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/specification")
	public ResponseEntity<List<UserDto>> getAllUsers(@RequestBody SpecificRequestDto specificRequestDto){
		Specification<User> specificUsers = specificSearchService.getSearchSpecification(specificRequestDto.getSearchRequestDto(),specificRequestDto.getGlobalOperator());
		List<User> userList=userRepository.findAll(specificUsers);
		List<UserDto> dtoList=userList.stream().map(users->this.modelMapper.map(users, UserDto.class)).collect(Collectors.toList());
		return new ResponseEntity<List<UserDto>>(dtoList,HttpStatus.OK);
	}
}
