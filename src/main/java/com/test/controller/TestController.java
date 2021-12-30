package com.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.test.model.Test;

@RestController
@RequestMapping("test")
public class TestController {
	@Autowired 
	private RestTemplate restTemplate; 
	private final String host="https://i-flightsapi-fuse-online.travelcloudplatformtest-8675e17b98237a374c107863a30b68f6-0000.us-south.containers.appdomain.cloud/flights/5151";
	@GetMapping("get")
	public ResponseEntity<Test> loginUser() {
		 ResponseEntity<Test> result = restTemplate.getForEntity(host, Test.class); 
	 	return result;
	}
}