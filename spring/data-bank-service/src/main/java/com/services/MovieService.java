package com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.hystrix.HystrixCommands;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.netflix.hystrix.HystrixCommandProperties;
import com.pojo.Author;
import com.pojo.Authors;
import com.pojo.Movie;
import com.pojo.Movies;

import reactor.core.publisher.Mono;

@Service
public class MovieService {

	private WebClient.Builder webclient;

	@Autowired
	public void setWebclient(WebClient.Builder webclient) {
		this.webclient = webclient;
	}
	
	
	public Mono<Movies> getMovies() {
	
		Mono<Movies> movs = this.webclient.build()
			.get()
			.uri("http://db-service/movie/all")
			.retrieve()						
			.bodyToMono(Movies.class);	
		
		return HystrixCommands
	            .from(movs)
	            .fallback(this.getMoviesFallback())
	            .commandName("getMovies")	            
	            .toMono();
	}
	
	
	public Mono<Movies> getMoviesFallback() {
		
		Movies movs_fall = new Movies();		
		movs_fall.movies.add(new Movie(0, "No movies"));
		return Mono.just(movs_fall);
	}


	public Mono<Authors> getNJAuthors(String header) {
		Mono<Authors> auths = this.webclient.build()
				.get()
				.uri("http://nodeJS-service/authors")
				.header("Macska", header)
				.retrieve()						
				.bodyToMono(Authors.class);	
			
		return HystrixCommands
	            .from(auths)
	            .fallback(this.getNJAuthorsFallback())
	            .commandName("getNJAuthors")	            
	            .toMono();
	}
	
	public Mono<Authors> getNJAuthorsFallback() {
		
		Authors auths_fall = new Authors();		
		auths_fall.authors.add(new Author("0", "No author"));
		return Mono.just(auths_fall);
	}

}



//.commandProperties(HystrixCommandProperties.defaultSetter()
//        .withExecutionTimeoutInMilliseconds(1000))





