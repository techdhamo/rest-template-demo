package com.otomate.loginservice.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.otomate.loginservice.model.UserRequest;
import com.otomate.loginservice.repository.UserRepository;
import com.otomate.loginservice.service.IUserLoginService;
import com.otomate.loginservice.util.JwtUtil;

@Component
public class UserLoginServiceImpl implements IUserLoginService {
	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;
	@Autowired
	private JwtUtil util;

	@Override
	public String authenticate(UserRequest user) {

		Optional<UserRequest> optUser = repo.findOneByEmail(user.getEmail());
		UserRequest dbUser = optUser.get();
		// Encode the password
		String encoddedPassword = dbUser.getPassword();
		// Validate the User Password
		boolean isValid = pwdEncoder.matches(user.getPassword(), encoddedPassword);
		//System.out.println(encoddedPassword);

		if (isValid) {

			return util.generateToken(user.getEmail());

		} else {
			throw new IllegalArgumentException("Invalid User");
		}
	}

}
