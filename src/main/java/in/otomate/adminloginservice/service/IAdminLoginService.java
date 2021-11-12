package in.otomate.adminloginservice.service;

import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.model.OTPRequest;
import in.otomate.adminloginservice.model.VerifyContact; 

public interface IAdminLoginService{
Admin findByEmail(String username);
 
Admin saveInfo(Admin a);

/**
 * @param id
 * @return
 */
Admin findById(Long id);  
 
 
/**
 * @param contact
 * @return
 */
Boolean sendOtp(OTPRequest contact);

/**
 * @param contact
 * @return
 */
Boolean verify(VerifyContact contact);  
}

