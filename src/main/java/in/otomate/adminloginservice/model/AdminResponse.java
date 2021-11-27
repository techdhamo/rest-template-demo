package in.otomate.adminloginservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminResponse {
	private String token; 
	private Long id;
	private String name;
	private String email; 
	private String mobile; 
	private String currentIndex; 
	private Long orgId; }
