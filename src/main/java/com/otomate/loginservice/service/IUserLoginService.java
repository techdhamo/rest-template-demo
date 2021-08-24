package com.otomate.loginservice.service;

import com.otomate.loginservice.model.UserRequest;

public interface IUserLoginService {
String authenticate(UserRequest user);

}
