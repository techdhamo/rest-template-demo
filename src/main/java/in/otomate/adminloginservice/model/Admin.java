package in.otomate.adminloginservice.model;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id; 
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter; 
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder 
public class Admin {
	@Id()
	@GeneratedValue(generator = "adminid")
	@GenericGenerator(name = "adminid", parameters = @Parameter(name = "prefix", value = ""), strategy = "in.otomate.common.util.BasicIDGenerator")
	private Long id;   
	private Long orgId;  
	private String fname;	 
	private String lname;
	@Column(unique = true)
	private String email; 
	@Column(unique = true)
	private String mobile;
	private String password; 
	private boolean enabled;
}