package com.otomate.loginservice.repository; 
import com.otomate.loginservice.model.UserRequest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
public interface UserRepository extends JpaRepository<UserRequest, String>{
 Optional<UserRequest> findOneByEmail(String email);
}
