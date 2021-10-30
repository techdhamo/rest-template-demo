package in.otomate.adminloginservice.service;

import in.otomate.adminloginservice.model.Admin;

public interface IAdminLoginService{
Admin findByEmail(String username);
 
Admin saveInfo(Admin a);

/**
 * @param id
 * @return
 */
Admin findById(Long id);
void addToElastic();
}

