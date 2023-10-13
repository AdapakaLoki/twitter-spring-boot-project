package com.twitter;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import com.twilio.Twilio;
import com.twitter.config.TwilioConfig;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
public class TwitterprojectApplication {

	@Autowired
	private TwilioConfig twilioConfig;
	
	public static void main(String[] args) {
		SpringApplication.run(TwitterprojectApplication.class, args);
	}
	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	@PostConstruct
	public void initTilio() {
		Twilio.init(twilioConfig.getAccountSid(), twilioConfig.getAuthToken());
	}

}
