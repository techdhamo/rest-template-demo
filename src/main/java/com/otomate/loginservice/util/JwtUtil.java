package com.otomate.loginservice.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	@Value("${app.secret}")
	private String secret;

	// Generating JWT Token
	public String generateToken(String subject) {
		return Jwts.builder().setSubject(subject).setIssuer("Otomate Technologies")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(15)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// Reading Claims
	public Claims getClaims(String token) {

		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJwt(token).getBody();
	}

	// Get Expire date
	public Date getExpDate(String token) {

		return getClaims(token).getExpiration();
	}

	// Read Subject
	public String getUsername(String token) {

		return getClaims(token).getSubject();
	}

	// Checking Token Expired or Not
	public boolean isExpired(String token) {

		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}

	// Validating Subject in token and Database and Expire date
	public boolean validateToken(String token, String username) {
		String usernameInToken = getUsername(token);
		return (username.equals(usernameInToken) && !isExpired(token));
	}
	//TODO: Verify Token
	//TODO: Refresh/Regenerate Token
	
}
