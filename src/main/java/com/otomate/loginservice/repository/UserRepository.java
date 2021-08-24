package com.otomate.loginservice.repository; 
import com.otomate.loginservice.model.UserRequest;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; 
public interface UserRepository extends JpaRepository<UserRequest, String>{
@Query(nativeQuery = true,value = "SELECT `email`, `password`  FROM `user` WHERE `email` like ?1")
Optional<UserRequest> findOneByEmail(String email);
}
