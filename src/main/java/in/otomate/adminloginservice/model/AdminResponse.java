package in.otomate.adminloginservice.model;


public class AdminResponse {
	private String token; 
	private Long id;
	private String name;
	private String email;
	 
	public AdminResponse() { 
	} 
	
	public AdminResponse(String token, Long id, String name, String email) {
		this.token = token;
		this.id = id;
		this.name = name;
		this.email = email;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
 
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	 
	
	
}
