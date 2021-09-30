package in.otomate.adminloginservice.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import in.otomate.adminloginservice.model.AdminDetails;
import in.otomate.adminloginservice.model.AdminModel;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.logger.Log; 

public class AdminDetailServiceImpl implements UserDetailsService {
	@Autowired 
	private IAdminLoginService service;

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
		return new AdminDetails(user);
	}

}
