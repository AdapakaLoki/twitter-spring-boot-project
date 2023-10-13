package com.twitter.component;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchduleExample {
	
	
	@Scheduled(cron = "*/10 * * * * *")//every ten seconds because first star 0 - 59 seconds
	//every one minute because second star 0 - 59 minutes 0-59 minutes
	//every one hour because third star 1 - 24 hours
	//fourth star represents weeks
	//fifth star represents months
	//sixth star represents years
	public void test() {
		LocalDateTime dt = LocalDateTime.now();
		System.out.println("hellow"+dt.now());
	}
}
