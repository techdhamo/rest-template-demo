/**
 * 
 */
package in.otomate.adminloginservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author dhamo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerifyContact {  
	private String contact; 
	private String otp;
}

