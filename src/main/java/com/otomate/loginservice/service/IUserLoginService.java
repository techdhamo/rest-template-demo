package com.otomate.loginservice.service;
  

import in.otomate.common.model.AdminModel;

public interface IUserLoginService{
AdminModel findByEmail(String username);

}
