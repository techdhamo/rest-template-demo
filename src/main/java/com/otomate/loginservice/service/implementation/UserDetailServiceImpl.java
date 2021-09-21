package com.otomate.loginservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.otomate.loginservice.model.MyUserDetails;  
import com.otomate.loginservice.service.IUserLoginService; 
import in.otomate.common.logger.Log;
import in.otomate.common.model.AdminModel;

public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired 
	private IUserLoginService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		AdminModel user=service.findByEmail(username);
		try {
		if (user==null) {
			throw new UsernameNotFoundException("Username not Found");
		} 
		}catch (Exception e) {
			Log.error(this, e.getMessage());
		}
		return new MyUserDetails(user);
	}

}
