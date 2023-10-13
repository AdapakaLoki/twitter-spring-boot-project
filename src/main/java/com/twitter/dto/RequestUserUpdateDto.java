package com.twitter.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestUserUpdateDto {
	
	@Pattern(regexp="^[a-zA-Z]+$",message="Username must be alphanumeric with no spaces")
	private String firstName;
	
	@Pattern(regexp="^[a-zA-Z]+$",message="Username must be alphanumeric with no spaces")
	private String lastName;
	
	private String mobileNo;
	@Email
	private String email;
	
	private String password;
}
