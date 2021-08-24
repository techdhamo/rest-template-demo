package com.otomate.loginservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.otomate.loginservice.model.UserRequest;
import com.otomate.loginservice.model.UserResponse;
import com.otomate.loginservice.service.*;

//Controller Class which handle Client requests
@RestController
@RequestMapping("/user")
public class UserLoginController {

	@Autowired
	private IUserLoginService service;

	@RequestMapping("/login")
	// Save user in Database
	public ResponseEntity<UserResponse> saveUser(@RequestBody UserRequest user) {
		String token = service.authenticate(user);
					return ResponseEntity.ok(new UserResponse(token, "Success!"));


	}
}
