package in.otomate.adminloginservice.service.implementation; 

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.otomate.adminloginservice.controller.AdminLoginController;
import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.model.AdminResponse;
import in.otomate.adminloginservice.model.OTPRequest;
import in.otomate.adminloginservice.model.ValidationResponse;
import in.otomate.adminloginservice.model.Verification;
import in.otomate.adminloginservice.model.VerifyContact;
import in.otomate.adminloginservice.repository.AdminRepository;
import in.otomate.adminloginservice.repository.VerificationRepository;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.Exceptions.DataViolationException;
import in.otomate.common.Exceptions.DuplicateEntryException;
import in.otomate.common.Exceptions.InvalidDataException;
import in.otomate.common.Exceptions.NullDataException;
import in.otomate.common.Exceptions.SystemException;
import in.otomate.common.Exceptions.UserNotFoundException; 

@Service
public class AdminLoginServiceImpl implements IAdminLoginService{
	@Autowired
	private AdminRepository repo;  
	@Autowired
	private VerificationRepository vrepo;  
	@Autowired
	private BCryptPasswordEncoder encoder; 
@Autowired
OTPSender sender;
	@Transactional(readOnly = true)
	public Admin findByEmail(String username) { 
		if (username != null) {
			Optional<Admin> opt=repo.findOneByEmail(username);
			if(opt.isPresent()) 
				return opt.get();
			else
	throw new UserNotFoundException(username, this, "Invalid username or password");
		}else {
			throw new NullDataException(username, this, "username is null");
		}

	}

 
	
	@Transactional(readOnly = true)
	public Admin findById(Long id) { 
		if (id != null) {
			Optional<Admin> opt=repo.findById(id);
			if(opt.isPresent()) 
				return opt.get();
			else
	throw new UserNotFoundException(null, this, "Invalid username or password");
		}else {
			throw new NullDataException(null, this, "username is null");
		}

	}

	@Override
	public ValidationResponse sendOtp(OTPRequest contact) {
		if (contact != null) {
			 if (contact.getContact().contains("+")) {   
					try {
						Optional<Admin> adminRecord=repo.findOneByMobile(contact.getContact());
						if (adminRecord.isPresent()) {
							throw new DuplicateEntryException(null, this, "Mobile Already registered by Someone");
						}else {							
						Verification verification=vrepo.findByContact(contact.getContact());
					if (verification == null) { 
						String otp=sender.send(contact.getContact());
						verification=Verification.builder()
							.contact(contact.getContact())
							.otp(otp)
							.build();
					}else {

						String otp=sender.send(contact.getContact());
					 
						verification.setOtp(otp);
					}	
					vrepo.save(verification);
					return ValidationResponse.builder().contact(contact.getContact()).status(true).build();
					
						}
					}catch (RuntimeException e) {
						throw new SystemException(null, this, e.getMessage());  
					}
				}else if (contact.getContact().contains("@")) {
					{   
						try {
							Optional<Admin> adminRecord=repo.findOneByEmail(contact.getContact());
							if (adminRecord.isPresent()) {
								throw new DuplicateEntryException(null, this, "Email Already registered by Someone");
							}else {							
							Verification verification=vrepo.findByContact(contact.getContact());
						if (verification == null) { 
							String otp=sender.send(contact.getContact());
							verification=Verification.builder()
								.contact(contact.getContact())
								.otp(otp)
								.build();
						}else {

							String otp=sender.send(contact.getContact());
						 
							verification.setOtp(otp);
						}	
						vrepo.save(verification);
						return ValidationResponse.builder().contact(contact.getContact()).status(true).build();
						
							}
						}catch (RuntimeException e) {
							throw new SystemException(null, this, e.getMessage());  
						}
					}
			}else {
				throw new InvalidDataException(null, this, "Invalid Data Provided : "+contact.getContact());
			}
			
		}else {
			throw new NullDataException(null, this, "mobile number can't be null");
		}
		 
	}

	@Override
	public ValidationResponse verify(VerifyContact contact) {
		try {
			Verification verification= vrepo.findByContact(contact.getContact());
		if (verification.getOtp().equals(contact.getOtp())) {
			verification.setActive(true);
			verification.setVerified(true);
			vrepo.save(verification);
			return ValidationResponse.builder().contact(contact.getContact()).status(true).build();
			
			
		}else {
			throw new InvalidDataException(null, this, " Data Mismatch"); 
		}
		} catch (IllegalArgumentException e) {

			throw new NullDataException(null, this, "Invalid Data provided : "+e.getMessage());
		} catch (RuntimeException e) {

			throw new SystemException(null, this, e.getMessage());
		} catch (Exception e) {

			throw new SystemException(null, this, e.getMessage());
		}
			
		}



	@Override
	public Admin saveInfo(Admin admin){
		if (admin != null) {
			Admin a=null;
			try {
				 admin.setPassword(encoder.encode(admin.getPassword())); 
						
						Verification  verifiedMail=vrepo.findById(admin.getEmail()).get();
						Verification  verifiedMobile=vrepo.findById(admin.getMobile()).get();
						if (verifiedMail.getVerified() && verifiedMobile.getVerified()) {
							admin.setEnabled(true);
							try {
							a=repo.save(admin);   
								
							} catch (Exception e) { 

								throw new SystemException(admin.getEmail(), this, e.getMessage()+" tested");
							}
							verifiedMail.setAdminId(a.getId());
							verifiedMobile.setAdminId(a.getId());
							vrepo.save(verifiedMail);
							vrepo.save(verifiedMobile); 
							 return a;
						}else {
							throw new InvalidDataException(admin.getEmail(), this, "Non Verified data provided for Admin");
							
						}
			} catch (IllegalArgumentException e) { 
				throw new InvalidDataException(admin.getEmail(), this, "Invalid data provided for Admin");
			} catch (DataIntegrityViolationException e) {
				 if(e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException) {
					 throw new DataViolationException(admin.getEmail(), this, e.getCause().toString()+" Testing");
				    } else if (ExceptionUtils.getRootCause(e) instanceof SQLIntegrityConstraintViolationException) {

						 throw new DataViolationException(admin.getEmail(), this, e.getRootCause().toString()+" Testing");
					} {
				    	throw new DataViolationException(admin.getEmail(), this, e.getMessage()+" Testing");
				    }
				 
			 
			}catch (NoSuchElementException e) { 
				admin.setEnabled(false);
				 repo.save(admin);  
				throw new DataViolationException(admin.getEmail(), this, "Admin disabled because Records miss match with Verification data "+admin.getEmail()+admin.getMobile());
			} catch (RuntimeException e) {

				throw new SystemException(admin.getEmail(), this, e.getMessage()+" tested");
			}catch (Exception e) {

				throw new SystemException(admin.getEmail(), this, e.getMessage()+" tested");
			}
		}else {
			throw new NullDataException(null, this, " Admin instance is Null");
		}
	}
	}

 
 
