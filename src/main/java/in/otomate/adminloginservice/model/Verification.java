/**
 * 
 */
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
 * @author dhamo
 *
 */ 
@Data
@Entity
@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder  
public class Verification{
		@Id() 
		private String contact; 
		@Column(nullable = false)
		private String otp; 
		private Boolean verified;
		private Long adminId;
		private boolean active;
}
