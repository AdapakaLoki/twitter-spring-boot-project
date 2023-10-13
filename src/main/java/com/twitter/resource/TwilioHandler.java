package com.twitter.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.twitter.dto.PasswordResetRequestDto;
import com.twitter.service.TwilioOtpService;

import reactor.core.publisher.Mono;

@Component
public class TwilioHandler {
	@Autowired
	private TwilioOtpService twilioOtpService;
	
	public Mono<ServerResponse> sendOtp(ServerRequest serverRequest){
		return serverRequest.bodyToMono(PasswordResetRequestDto.class)
				.flatMap(dto->twilioOtpService.sendOtpForPasswordReset(dto))
				.flatMap(dto->ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)));
				
	}
	public Mono<ServerResponse> validateOtp(ServerRequest serverRequest){
		return serverRequest.bodyToMono(PasswordResetRequestDto.class)
				.flatMap(dto->twilioOtpService.validateOtp(dto.getOneTimePassword(),dto.getUserName()))
				.flatMap(dto->ServerResponse.status(HttpStatus.OK).bodyValue(dto));
				
	}
	
}
