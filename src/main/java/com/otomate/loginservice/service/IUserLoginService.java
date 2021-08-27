package com.otomate.loginservice.service;

import java.util.Optional;

import com.otomate.loginservice.model.UserRequest;

public interface IUserLoginService{
UserRequest findByEmail(String username);

}
