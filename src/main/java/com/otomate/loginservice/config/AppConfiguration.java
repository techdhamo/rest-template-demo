package com.otomate.loginservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.otomate.loginservice.service.implementation.UserDetailServiceImpl;

@Configuration
public class AppConfiguration {
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public UserDetailsService service() {
		return new UserDetailServiceImpl();
	} 
}
