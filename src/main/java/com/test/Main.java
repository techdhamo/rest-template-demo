package com.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate; 

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
	}
	@Bean
	RestTemplate getTemplate() {
		return new RestTemplate();
	}
}
