package com.otomate.loginservice.repository;  

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.otomate.loginservice.model.AdminModel; 
public interface UserRepository extends JpaRepository<AdminModel, String>{
Optional<AdminModel> findOneByEmail(String email);
}
