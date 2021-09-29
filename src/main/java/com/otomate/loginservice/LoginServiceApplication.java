package com.otomate.loginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.otomate.loginservice.config.SecurityConfig;
import com.otomate.loginservice.controller.AdminLoginController;
import com.otomate.loginservice.filter.SecurityFilter;
import com.otomate.loginservice.service.implementation.UserLoginServiceImpl;
import com.otomate.loginservice.util.JwtUtil;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackageClasses = { SecurityFilter.class,JwtUtil.class,AdminLoginController.class,SecurityConfig.class,UserLoginServiceImpl.class})
public class LoginServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}
}
