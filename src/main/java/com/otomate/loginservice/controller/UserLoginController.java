package com.otomate.loginservice.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otomate.loginservice.model.UserRequest;
import com.otomate.loginservice.model.UserResponse;
import com.otomate.loginservice.service.IUserLoginService;
import com.otomate.loginservice.util.JwtUtil;
@RestController
@RequestMapping("/user")
public class UserLoginController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private IUserLoginService service;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/update")
	public ResponseEntity<String> saveUser() { 
		return ResponseEntity.ok("User Updated");
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest)
	{

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						userRequest.getEmail(), 
						userRequest.getPassword()
						)
				);

		String token=jwtUtil.generateToken(userRequest.getEmail());

		return ResponseEntity.ok(new UserResponse(token,"Success!"));
	}
	
	@PostMapping("/verify")
	public ResponseEntity<String> accessUserData(Principal p) {
		return ResponseEntity.ok("Hello user:"+p.getName());
	}


}