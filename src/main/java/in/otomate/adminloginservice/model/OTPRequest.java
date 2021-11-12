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
public class OTPRequest { 
	private String fname;
	private String lname;
	private String contact; 
}

