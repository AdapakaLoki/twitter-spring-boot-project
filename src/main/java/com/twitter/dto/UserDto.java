package com.twitter.dto;

import java.util.List;
import java.util.Set;

import com.twitter.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
	@NotEmpty(message = "firstname must be required")
	@Size(min = 4,max = 25)
	@Pattern(regexp="^[a-zA-Z]+$",message="Username must be alphanumeric with no spaces")
	private String firstName;
	@NotEmpty(message = "lastName must be required")
	@Pattern(regexp="^[a-zA-Z]+$",message="Username must be alphanumeric with no spaces")
	private String lastName;
	@NotEmpty(message = "mobileNo  must be required")
	private String mobileNo;
	@Email
	@NotEmpty(message = "email  must be required")
	private String email;
	@NotEmpty(message = "password  must be required")
	private String password;
	
	private Set<Role> roles;
	
	private List<PostDto> posts;
	
	private Set<CommentDto> comments;
}
