package com.jwt;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;



import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtTokenVerifier extends OncePerRequestFilter {

	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		String authorizationHeader = request.getHeader("Authorization");
		
		if(authorizationHeader == null || authorizationHeader.isEmpty() || !authorizationHeader.startsWith("Bearer "))
		{
			filterChain.doFilter(request, response);
			return;
		}
		
		String key = "secretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecretsecret";
		String token = authorizationHeader.replace("Bearer ", "");
		
		try 
		{
			Jws<Claims> claims = Jwts.parser()
					.setSigningKey(Keys.hmacShaKeyFor(key.getBytes()))
					.parseClaimsJws(token);

			Claims body = claims.getBody();
			
			String username = body.getSubject();
			
			List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities");
			
			Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
																.map(m -> new SimpleGrantedAuthority(m.get("authority")))
																.collect(Collectors.toSet());
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, simpleGrantedAuthorities);
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		catch (Exception e) {e.printStackTrace();}
		
		filterChain.doFilter(request, response);		
	}

}

















