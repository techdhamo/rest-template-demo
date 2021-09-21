package com.otomate.loginservice.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.otomate.common.model.AdminModel;

public class MyUserDetails implements UserDetails { 
	AdminModel user;

	public MyUserDetails(AdminModel user) {
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
		return user.isEnabled();
	}

}