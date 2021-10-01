package in.otomate.adminloginservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List; 

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.otomate.adminloginservice.logger.Log;

 
public class AdminDetails implements UserDetails { 
	AdminModel user;

	public AdminDetails(AdminModel user) {
	this.user = user;
}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { 
List<SimpleGrantedAuthority> authorities=new ArrayList<>(); 
	authorities.add(new SimpleGrantedAuthority("SUPER_ADMIN")); 
		return authorities;
	}
	@Override
	public String getPassword() { 
		return user.getPassword();
	}

	@Override
	public String getUsername() { 
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() { 
		return true;
	}

	@Override
	public boolean isAccountNonLocked() { 
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() { 
		return true;
	}

	@Override
	public boolean isEnabled() { 
		try {
			return user.isEnabled();
		} catch (Exception e) {
			Log.info(this, "  User object in null");
			return false; 
		}
		
	}

}
