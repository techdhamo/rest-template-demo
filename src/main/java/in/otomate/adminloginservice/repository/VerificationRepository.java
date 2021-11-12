package in.otomate.adminloginservice.repository;  

import org.springframework.data.jpa.repository.JpaRepository;

import in.otomate.adminloginservice.model.Verification; 
public interface VerificationRepository extends JpaRepository<Verification, Long>{

	/**
	 * @param contact
	 * @return 
	 */
	Verification findByContact(String contact); 
}
