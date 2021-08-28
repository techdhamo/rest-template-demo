package com.otomate.loginservice.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.otomate.loginservice.logger.Log;
import com.otomate.loginservice.model.UserRequest;
import com.otomate.loginservice.model.UserResponse;
import com.otomate.loginservice.service.IUserLoginService;
import com.otomate.loginservice.util.JwtUtil;

import io.jsonwebtoken.impl.DefaultClaims;

@CrossOrigin
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
	public ResponseEntity<String> saveUser(Principal p) {

		Log.logger.error("Details updated by : " + p.getName());
		return ResponseEntity.ok("User Updated");
	}

	@PostMapping("/login")
	public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest) {

		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userRequest.getEmail(), userRequest.getPassword()));

		String token = jwtUtil.generateToken(userRequest.getEmail());
 
		return ResponseEntity.ok(new UserResponse(token, "Success!"));
	}

	@PostMapping("/verify")
	public ResponseEntity<String> accessUserData(Principal p) {
		return ResponseEntity.ok("Hello user:" + p.getName());
	}

	@GetMapping("/refreshtoken")
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		String bearerToken = request.getHeader("Authorization");
		String expiryToken = bearerToken.replace("Bearer", "");
		DefaultClaims claims = (DefaultClaims) jwtUtil.getClaims(expiryToken);
		// DefaultClaims claims = (DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new UserResponse(token, "refreshed"));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}