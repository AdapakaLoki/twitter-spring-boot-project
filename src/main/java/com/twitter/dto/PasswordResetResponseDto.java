package com.twitter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetResponseDto {
	
	private otpStatus status;
	private String message;
	
	public enum otpStatus{
		DELIVERED,FAILD;
	}
	
}
