package com.otomate.loginservice.service;

import com.otomate.loginservice.model.AdminModel;

public interface IUserLoginService{
AdminModel findByEmail(String username);

}
