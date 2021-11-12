package in.otomate.adminloginservice.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.model.AdminResponse;
import in.otomate.adminloginservice.model.OTPRequest;
import in.otomate.adminloginservice.model.VerifyContact;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.util.JwtUtil;
import io.jsonwebtoken.impl.DefaultClaims;

//@CrossOrigin(origins = Hosts.CLIENT_HOST) 
@RestController
@RequestMapping("admin")
public class AdminLoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	IAdminLoginService service;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("login")
	public ResponseEntity<AdminResponse> loginUser(@RequestBody Admin a) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(a.getEmail(), a.getPassword()));
		String token = jwtUtil.generateToken(a.getEmail());
		Admin admin = service.findByEmail(a.getEmail());
		return ResponseEntity.ok(new AdminResponse(token, admin.getId(), admin.getFname() + " " + admin.getLname(),
				admin.getEmail(), admin.getMobile(), admin.getOrgId()));
	}

	@GetMapping("refreshtoken")
	public ResponseEntity<AdminResponse> refreshtoken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		String expiryToken = bearerToken.replace("Bearer ", "");

		DefaultClaims claims = (DefaultClaims) jwtUtil.getClaims(expiryToken);
		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.generateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		String username = jwtUtil.getUsername(token);
		Admin admin = service.findByEmail(username);
		return ResponseEntity.ok(new AdminResponse(token, admin.getId(), admin.getFname() + " " + admin.getLname(),
				admin.getEmail(), admin.getMobile(), admin.getOrgId()));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	@PostMapping("register")
	Admin test(@RequestBody Admin a) {
		return service.saveInfo(a);
	}

	@GetMapping("{id}")
	Admin test(@PathVariable Long id) {
		return service.findById(id);
	}
	@GetMapping("verify")
	Boolean verify(@RequestBody  VerifyContact contact) {
		return service.verify(contact);
	}
		@GetMapping("send")
		Boolean send(@RequestBody  OTPRequest contact) {
			return service.sendOtp(contact);
	}
}