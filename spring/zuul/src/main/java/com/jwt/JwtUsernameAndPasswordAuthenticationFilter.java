package com.jwt;


import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
		
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}


	// 1. this will be invoked in the chain
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		
		
		try 
		{
			UsernameAndPasswordAuthenticationRequest authreq = new ObjectMapper()
																.readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(authreq.getUsername(), authreq.getPassword());
			
			authentication = authenticationManager.authenticate(authentication);
			
			return authentication;
		} 
		catch (Exception e) {	throw new RuntimeException(e); }		
	}
	
	
	// 2. this vill be invoked after "attemptAuthentication", send back a token
	@Override
	protected void successfulAuthentication(	HttpServletRequest request, 
												HttpServletResponse response, 
												FilterChain chain, 
												Authentication authResult) throws IOException, ServletException {
		
		String key = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
		
		String token = Jwts.builder()
							.setSubject(authResult.getName())
							.claim("authorities", authResult.getAuthorities())
							.setIssuedAt(new Date())
							.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
							.signWith(Keys.hmacShaKeyFor(key.getBytes()))
							.compact();
		
		response.addHeader("Authorization", "Bearer " + token);
	}

}
