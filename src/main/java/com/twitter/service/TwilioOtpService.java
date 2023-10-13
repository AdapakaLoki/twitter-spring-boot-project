package com.twitter.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.twitter.config.TwilioConfig;
import com.twitter.dto.PasswordResetRequestDto;
import com.twitter.dto.PasswordResetResponseDto;

import reactor.core.publisher.Mono;

@Service
public class TwilioOtpService {
	
	@Autowired
	private TwilioConfig twilioConfig;
	
	Map<String, String> otpMap= new HashMap<>();

	public Mono<PasswordResetResponseDto> sendOtpForPasswordReset(PasswordResetRequestDto passwordResetRequestDto) {
		PasswordResetResponseDto passwordResetResponseDto=null;
		try {
		PhoneNumber to = new PhoneNumber(passwordResetRequestDto.getPhoneNumber());
		PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
		String otp=generateOtp();
		String otpMessage="Dear customer , your otp is ##" +otp+ "## use the passcode to complete  your transection. thank you";
		Message message = Message.creator(to,from,otpMessage)
	            .create();
		otpMap.put(passwordResetRequestDto.getUserName(), otp);
		passwordResetResponseDto=new PasswordResetResponseDto(PasswordResetResponseDto.otpStatus.DELIVERED,otpMessage);
		}catch (Exception e) {
			passwordResetResponseDto=new PasswordResetResponseDto(PasswordResetResponseDto.otpStatus.FAILD,e.getMessage());
		}
		return Mono.just(passwordResetResponseDto);
	}
	
	public Mono<String> validateOtp(String userInputOtp,String userName){
		if(userInputOtp.equals(otpMap.get(userName))) {
			return Mono.just("validOtp please procied");
		}else {
			return Mono.error(new IllegalArgumentException("invalid otp please try"));
		}
	}
	
	private String generateOtp() {
		return new DecimalFormat("000000")
				.format(new Random().nextInt(999999));
	}
}
