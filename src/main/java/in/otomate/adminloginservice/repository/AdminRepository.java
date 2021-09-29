package in.otomate.adminloginservice.repository;  

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.otomate.adminloginservice.model.AdminModel; 
public interface AdminRepository extends JpaRepository<AdminModel, String>{
Optional<AdminModel> findOneByEmail(String email);
}
