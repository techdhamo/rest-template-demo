package in.otomate.adminloginservice.model;


public class AdminResponse {
	private String token;
	private String message;
	public AdminResponse(String token, String message) { 
		this.token = token;
		this.message = message;
	}
	public AdminResponse() {  
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
