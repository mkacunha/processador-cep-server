package com.mkacunha.processadorcep;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class ApplicationBeanConfiguration {

	public static final int THREAD_POOL_SIZE = 20;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
}
