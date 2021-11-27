package in.otomate.adminloginservice.service;

import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.model.OTPRequest;
import in.otomate.adminloginservice.model.ValidationResponse;
import in.otomate.adminloginservice.model.VerifyContact; 

public interface IAdminLoginService{
Admin findByEmail(String username);
 
public Admin saveInfo(Admin admin);

/**
 * @param id
 * @return
 */
Admin findById(Long id);  
 
 
/**
 * @param contact
 * @return
 */
ValidationResponse sendOtp(OTPRequest contact);

/**
 * @param contact
 * @return
 */
ValidationResponse verify(VerifyContact contact);  
}

