package in.otomate.adminloginservice.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.repository.AdminRepository;
import in.otomate.adminloginservice.service.IAdminLoginService; 

@Service
public class AdminLoginServiceImpl implements IAdminLoginService{
	@Autowired
	private AdminRepository repo; 
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public Admin findByEmail(String username) { 
		Optional<Admin> opt=repo.findOneByEmail(username);
		if(opt.isPresent()) 
			return opt.get();
		else return null;	}

	@Override
	public Admin saveInfo(Admin a) {
		 a.setPassword(encoder.encode(a.getPassword()));
		return repo.save(a);
	}
	

}