package com.twitter.controlar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twitter.dto.Root;
import com.twitter.service.MovieService;

@RestController
@RequestMapping("/api/v5")
public class MovieControlar {
	@Autowired
	private MovieService movieService;
	
	@GetMapping("/movies")
	public ResponseEntity<Root> getAllMovies(){
		Root root=movieService.findAll();
		return new ResponseEntity<Root>(root,HttpStatus.OK);
	}
}
