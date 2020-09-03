package com.controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.Authors;
import com.pojo.Movies;
import com.services.MovieService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mdc")
public class MovieDataController {

	private MovieService movieservice;
	
	@Autowired	
	public void setMovieservice(MovieService movieservice) {
		this.movieservice = movieservice;
	}



	@GetMapping("/all")
	public Mono<Movies> getMovies(HttpServletRequest req) {		
		
//		UserCredentials credentials = readUserCredentials(req);
		
		Enumeration<String> heds = req.getHeaderNames();
		
		String hed = req.getHeader("Macska");
		
		return movieservice.getMovies();
	}
	
	@GetMapping("/NJauthor")
	public Mono<Authors> getNJAuthors(HttpServletRequest req) {		
		
		// also add JWT header, and nodeJS figure out the access...
		
		Enumeration<String> heds = req.getHeaderNames();
		
		String hed = req.getHeader("Macska");
		
		return movieservice.getNJAuthors(hed);
	}
	
	
//	@GetMapping("/all")
//	public Movies getMovies() {		
//		return movieservice.getMovies();
//	}
}
