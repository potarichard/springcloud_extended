package com.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.Movie;
import com.pojo.Movies;

@RestController
@RequestMapping("/movie")
public class MovieController {

	@GetMapping("/all")
	public Movies getAllMovies() {
		
		Movies movies = new Movies();
		
		movies.movies.add(new Movie(2000, "Land"));
		movies.movies.add(new Movie(2001, "Dracula"));
		
		return movies;
	}
}
