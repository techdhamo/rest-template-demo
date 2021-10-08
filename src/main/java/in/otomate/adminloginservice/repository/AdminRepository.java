package in.otomate.adminloginservice.repository;  

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.otomate.adminloginservice.model.Admin; 
public interface AdminRepository extends JpaRepository<Admin, String>{
Optional<Admin> findOneByEmail(String email);
}
