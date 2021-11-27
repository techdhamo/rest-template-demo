package in.otomate.adminloginservice.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.otomate.adminloginservice.model.Admin;
import in.otomate.adminloginservice.model.AdminResponse;
import in.otomate.adminloginservice.model.ErrorResponse;
import in.otomate.adminloginservice.model.OTPRequest;
import in.otomate.adminloginservice.model.ValidationResponse;
import in.otomate.adminloginservice.model.VerifyContact;
import in.otomate.adminloginservice.service.IAdminLoginService;
import in.otomate.common.Exceptions.DataViolationException;
import in.otomate.common.Exceptions.InputOutputException;
import in.otomate.common.Exceptions.InvalidDataException;
import in.otomate.common.Exceptions.InvalidTokenException;
import in.otomate.common.Exceptions.NoContentException;
import in.otomate.common.Exceptions.NullDataException;
import in.otomate.common.Exceptions.SystemException;
import in.otomate.common.Exceptions.TokenExpiredException;
import in.otomate.common.Exceptions.UserNotFoundException;
import in.otomate.common.Exceptions.ValidationException;
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
				admin.getEmail(), admin.getMobile(),admin.getCurrentIndex(), admin.getOrgId()));
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
				admin.getEmail(), admin.getMobile(), admin.getCurrentIndex(),admin.getOrgId()));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}

	@PostMapping("register")
	ResponseEntity<AdminResponse> register(@RequestBody Admin a) {
		  
		Admin admin= service.saveInfo(a);
		 String token = jwtUtil.generateToken(admin.getEmail()); 
		return ResponseEntity.ok(new AdminResponse(token, admin.getId(), admin.getFname() + " " + admin.getLname(),
				admin.getEmail(), admin.getMobile(),admin.getCurrentIndex(), admin.getOrgId()));
		
		
		
	}

	@GetMapping("{id}")
	Admin test(@PathVariable Long id) {
		return service.findById(id);
	}
	@PostMapping("verify")
	ValidationResponse verify(@RequestBody  VerifyContact contact) {
		return service.verify(contact);
	}
		@PostMapping("send")
		ValidationResponse send(@RequestBody  OTPRequest contact) {
			return service.sendOtp(contact);
	}
		
		@ExceptionHandler(SystemException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ResponseBody
	    public ErrorResponse errorResponse(SystemException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()+5); 
	        return errorResponse;
	    }
		@ExceptionHandler(ValidationException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ErrorResponse errorResponse(ValidationException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(UserNotFoundException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    @ResponseBody
	    public ErrorResponse errorResponse(UserNotFoundException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(TokenExpiredException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    @ResponseBody
	    public ErrorResponse errorResponse(TokenExpiredException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(NullDataException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ErrorResponse errorResponse(NullDataException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    } 
		@ExceptionHandler(NoContentException.class)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    @ResponseBody
	    public ErrorResponse errorResponse(NoContentException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(InvalidTokenException.class)
	    @ResponseStatus(HttpStatus.UNAUTHORIZED)
	    @ResponseBody
	    public ErrorResponse errorResponse(InvalidTokenException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(InvalidDataException.class)
	    @ResponseStatus(HttpStatus.BAD_REQUEST)
	    @ResponseBody
	    public ErrorResponse errorResponse(InvalidDataException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(InputOutputException.class)
	    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	    @ResponseBody
	    public ErrorResponse errorResponse(InputOutputException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    }
		@ExceptionHandler(DataViolationException.class)
	    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	    @ResponseBody
	    public ErrorResponse errorResponse(DataViolationException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        errorResponse.setMessage(ex.getMessage()); 
	        return errorResponse;
	    } 
	  
		@ExceptionHandler(DataIntegrityViolationException.class)
	    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	    @ResponseBody
	    public ErrorResponse errorResponse(DataIntegrityViolationException ex) {

	        ErrorResponse errorResponse = new ErrorResponse();
	        if(ex.getRootCause() instanceof SQLIntegrityConstraintViolationException | ex.getRootCause() instanceof ConstraintViolationException)
	        errorResponse.setMessage(ex.getRootCause().getMessage()+2);
	        else {
	        	  errorResponse.setMessage(ex.getMessage()+2);
			}
	        return errorResponse;
	    } 
		 
}