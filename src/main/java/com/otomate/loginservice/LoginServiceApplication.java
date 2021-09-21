package com.otomate.loginservice;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("in.otomate.common.model.AdminModel")
@ComponentScan(basePackages = { "in.otomate.common.model.AdminModel" })
@EntityScan("in.otomate.common.model.AdminModel")   
public class LoginServiceApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
		
	} 
}
