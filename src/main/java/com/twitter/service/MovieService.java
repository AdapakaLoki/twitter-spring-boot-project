package com.twitter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.twitter.dto.Root;

@Service
public class MovieService {

	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${movie.url}")
	public String url;
	
	public Root findAll() {
		Root movies=null;
		HttpHeaders headers = new HttpHeaders();
		headers.set("X-RapidAPI-Key", "2d0b45bc69mshdd44c29d9eb9057p1e3827jsn222643d7c9a8");
		headers.set("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com");
		HttpEntity<Root> entity = new HttpEntity<>(headers);
		movies=restTemplate.exchange(url, HttpMethod.GET, entity, Root.class).getBody();
		return movies;
	}
}
