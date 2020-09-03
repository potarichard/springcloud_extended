package com.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pojo.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/{name}")
	public User findUserByName(@PathVariable("name") String name) {
		
		return new User("Halo", "d123x");
	}
}
