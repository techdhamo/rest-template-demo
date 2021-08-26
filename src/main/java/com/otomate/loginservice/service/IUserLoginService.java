package com.otomate.loginservice.service;

import java.util.Optional;

import com.otomate.loginservice.model.UserRequest;

public interface IUserLoginService{
String authenticate(UserRequest user);

Optional<UserRequest> findByUsername(String username);

}
