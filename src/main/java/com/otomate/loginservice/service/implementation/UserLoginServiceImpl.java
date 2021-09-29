package com.otomate.loginservice.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.otomate.loginservice.model.AdminModel;
import com.otomate.loginservice.repository.UserRepository;
import com.otomate.loginservice.service.IUserLoginService; 

@Service
public class UserLoginServiceImpl implements IUserLoginService{
	@Autowired
	private UserRepository repo; 

	@Transactional(readOnly = true)
	public AdminModel findByEmail(String username) { 
		Optional<AdminModel> opt=repo.findOneByEmail(username);
		if(opt.isPresent()) 
			return opt.get();
		else return null;	}
	

}