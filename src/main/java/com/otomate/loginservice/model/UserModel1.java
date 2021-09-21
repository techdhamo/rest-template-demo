package com.otomate.loginservice.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class UserModel1 {
	 
	@Id 
	private String employee_id;
	private String email;
	private String firstName;
	private String lastName;
	private String department;
	private String skypeid;
	private String phone;
	private String password;
	 private boolean enabled=true;
		@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
		@JoinTable(
				name = "users_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id")
				)
		private Set<Role> roles = new HashSet<>();  


}