package in.otomate.adminloginservice.service.implementation;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; 
import in.otomate.adminloginservice.model.AdminDetails;
import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.logger.Log;
@Primary  
@Service
public class AdminDetailServiceImpl implements UserDetailsService {
	@Autowired 
	private IAdminLoginService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user=service.findByEmail(username);
		try {
		if (Objects.isNull(user)) {

			throw new UsernameNotFoundException("Username not Found");
			
		} 
		}catch (Exception e) {
			Log.error(this, e.getMessage());
		} 
		return new AdminDetails(user);
	}

}
