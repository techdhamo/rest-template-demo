package in.otomate.adminloginservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter; 
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * ORGANIZATION MODEL CLASS ================ This Class contains all private
 * user fields and getter setter functions
 * 
 * @author OTOMATE TECHNOLOGIES
 *
 */
@Data
@Entity
@Component
@Table(name = "admin_details")
public class AdminModel {
	@Id()
	@GeneratedValue(generator = "prod-generator")
	@GenericGenerator(name = "prod-generator", parameters = @Parameter(name = "prefix", value = ""), strategy = "in.otomate.adminloginservice.util.AdminIDGenerator")
	private Long adminId; 
	private String firstName;
	private String lastName;
	@Column(unique = true)
	private String email; 
	@Column(unique = true)
	private String mobile;
	private String password; 
	private boolean enabled=true;
}