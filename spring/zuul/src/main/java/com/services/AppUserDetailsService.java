package com.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.entities.User;

@Service
public class AppUserDetailsService implements UserDetailsService {

	
	private UserService user_service;
	
	@Autowired	
	public void setUser_service(UserService user_service) {
		this.user_service = user_service;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = user_service.find(username);
				
		return user;
	}
	
}
