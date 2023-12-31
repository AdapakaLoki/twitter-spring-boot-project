package com.twitter.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TwilioRouterConfig {
	@Autowired	
	private TwilioHandler twilioHandler;
	
	@Bean
	public RouterFunction<ServerResponse> handleSms(){
		return RouterFunctions.route()
				.POST("/router/sendOTP",twilioHandler::sendOtp)
				.POST("/router/validateOTP",twilioHandler::validateOtp).build();
				}
}
