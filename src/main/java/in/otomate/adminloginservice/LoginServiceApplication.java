package in.otomate.adminloginservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import in.otomate.adminloginservice.config.SecurityConfig;
import in.otomate.adminloginservice.controller.AdminLoginController;
import in.otomate.adminloginservice.filter.SecurityFilter;
import in.otomate.adminloginservice.service.implementation.AdminLoginServiceImpl;
import in.otomate.adminloginservice.util.JwtUtil;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackageClasses = { SecurityFilter.class,JwtUtil.class,AdminLoginController.class,SecurityConfig.class,AdminLoginServiceImpl.class})
public class LoginServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LoginServiceApplication.class, args);
	}
}
