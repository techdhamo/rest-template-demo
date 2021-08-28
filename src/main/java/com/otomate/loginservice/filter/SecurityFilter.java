package com.otomate.loginservice.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.otomate.loginservice.logger.Log;
import com.otomate.loginservice.util.JwtUtil;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserDetailsService userDetailsService; 
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String bearerToken = request.getHeader("Authorization");

		if (bearerToken != null) {

			String token = bearerToken.replace("Bearer", "");
			String username = jwtUtil.getUsername(token);
			
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails user = userDetailsService.loadUserByUsername(username);
				boolean isValid = jwtUtil.validateToken(token, user.getUsername());
				if (isValid) {
					Log.logger.info("Token Validated");
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username,
							user.getPassword(), user.getAuthorities());

					authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authToken);

				}
			}
		} else {

			Log.logger.info("Authorization Token is Empty");
		}
		filterChain.doFilter(request, response);
	}

}