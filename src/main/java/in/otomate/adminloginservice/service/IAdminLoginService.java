package in.otomate.adminloginservice.service;

import in.otomate.adminloginservice.model.AdminModel;

public interface IAdminLoginService{
AdminModel findByEmail(String username);

}
