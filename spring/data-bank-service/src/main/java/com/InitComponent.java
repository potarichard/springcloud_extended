package com;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InitComponent {

	@Bean
	@LoadBalanced
	public WebClient.Builder webclient() {
		return WebClient.builder();
		
	}
}
