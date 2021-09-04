package com.otomate.loginservice.service.implementation;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.stream.Collectors;

import com.otomate.loginservice.logger.Log;
import com.otomate.loginservice.model.UserRequest;
import com.otomate.loginservice.repository.UserRepository;
import com.otomate.loginservice.service.IUserLoginService; 

@Service
public class UserLoginServiceImpl implements IUserLoginService,UserDetailsService{
	@Autowired
	private UserRepository repo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder; 

	@Transactional(readOnly = true)
	public UserRequest findByEmail(String username) {
		Log.logger.debug("Testing>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+ username);
		Optional<UserRequest> opt=repo.findOneByEmail(username);
		if(opt.isPresent()) 
			return opt.get();
		return null;
	}

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException 
	{
		UserRequest user=findByEmail(username); 
		if(user==null) 
			throw new UsernameNotFoundException(
					new StringBuffer()
					.append("User name ")
					.append(username)
					.append(" not found!")
					.toString()
					);
		Set<String> roleList = new HashSet<>();   
		for (String r : user.getRoles().split(" ")) {
             
			roleList.add(r);
        }
  
		List<GrantedAuthority> authorities=roleList
				.stream()
				.map(
						role->new SimpleGrantedAuthority(role)
						)
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(
				username, 
				user.getPassword(), 
				authorities);
	}


}