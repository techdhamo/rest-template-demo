package in.otomate.adminloginservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.internal.lang.annotation.ajcDeclareAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.otomate.adminloginservice.model.AdminResponse;
import in.otomate.adminloginservice.util.JwtUtil;
import in.otomate.common.config.Path;
import io.jsonwebtoken.impl.DefaultClaims;

//@CrossOrigin(origins = Hosts.CLIENT_HOST) 
@RestController
@RequestMapping(Path.ADMIN_ROOT_PATH) 
public class AdminLoginController {

	@Autowired
	private AuthenticationManager authenticationManager; 

	@Autowired
	private JwtUtil jwtUtil; 
	@GetMapping(Path.ADMIN_LOGIN_PATH)
	public ResponseEntity<AdminResponse> loginUser(@RequestParam String username, @RequestParam String password) {
 		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(username, password));

		String token = jwtUtil.generateToken(username);
 
		return ResponseEntity.ok(new AdminResponse(token, "Success!"));
	}
	 
	@GetMapping(Path.ADMIN_REFRESH_TOKEN_PATH)
	public ResponseEntity< AdminResponse > refreshtoken(HttpServletRequest request){
		String bearerToken = request.getHeader("Authorization");
		String expiryToken = bearerToken.replace("Bearer ", "");
		 
		DefaultClaims claims = (DefaultClaims) jwtUtil.getClaims(expiryToken); 
		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AdminResponse(token, "refreshed"));
	}
	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

}