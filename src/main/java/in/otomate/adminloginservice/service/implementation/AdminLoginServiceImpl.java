package in.otomate.adminloginservice.service.implementation; 

import java.util.Optional;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; 
import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.repository.AdminRepository; 
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.Exceptions.DataViolationException;
import in.otomate.common.Exceptions.InvalidDataException;
import in.otomate.common.Exceptions.NullDataException;
import in.otomate.common.Exceptions.SystemException;
import in.otomate.common.Exceptions.UserNotFoundException; 

@Service
public class AdminLoginServiceImpl implements IAdminLoginService{
	@Autowired
	private AdminRepository repo;  
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Transactional(readOnly = true)
	public Admin findByEmail(String username) { 
		if (username != null) {
			Optional<Admin> opt=repo.findOneByEmail(username);
			if(opt.isPresent()) 
				return opt.get();
			else
	throw new UserNotFoundException(username, this, "user not found");
		}else {
			throw new NullDataException(username, this, "username is null");
		}

	}

	@Override
	public Admin saveInfo(Admin admin) {
	if (admin != null) {
		try {
			 admin.setPassword(encoder.encode(admin.getPassword()));
				Admin adminUpdated= repo.save(admin);  
				return adminUpdated;
		} catch (IllegalArgumentException e) { 
			throw new InvalidDataException(admin.getEmail(), this, "Invalid data provided for Admin");
		} catch (DataIntegrityViolationException e) {
			throw new DataViolationException(admin.getEmail(), this, e.getRootCause().getMessage());
		}catch (Exception e) {

			throw new SystemException(admin.getEmail(), this, e.getMessage());
		}
	}else {
		throw new NullDataException(null, this, " Admin instance is Null");
	}
	}
	
	@Transactional(readOnly = true)
	public Admin findById(Long id) { 
		if (id != null) {
			Optional<Admin> opt=repo.findById(id);
			if(opt.isPresent()) 
				return opt.get();
			else
	throw new UserNotFoundException(null, this, "user not found");
		}else {
			throw new NullDataException(null, this, "username is null");
		}

	}
 
}