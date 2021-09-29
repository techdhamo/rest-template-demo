package in.otomate.adminloginservice.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.otomate.adminloginservice.model.AdminModel;
import in.otomate.adminloginservice.repository.AdminRepository;
import in.otomate.adminloginservice.service.IAdminLoginService; 

@Service
public class AdminLoginServiceImpl implements IAdminLoginService{
	@Autowired
	private AdminRepository repo; 

	@Transactional(readOnly = true)
	public AdminModel findByEmail(String username) { 
		Optional<AdminModel> opt=repo.findOneByEmail(username);
		if(opt.isPresent()) 
			return opt.get();
		else return null;	}
	

}