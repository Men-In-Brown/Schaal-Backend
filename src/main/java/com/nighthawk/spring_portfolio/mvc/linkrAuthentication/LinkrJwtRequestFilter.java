package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import java.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;
import io.jsonwebtoken.ExpiredJwtException;

// Component for filtering JWT requests
@Component
public class LinkrJwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private PersonDetailsService personDetailsService; // Service for managing person details

	@Autowired
	private LinkrJwtTokenUtil jwtTokenUtil; // Utility class for JWT token operations

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

		final Cookie[] cookies = request.getCookies(); // Retrieve cookies from the request
		String username = null;
		String jwtToken = null;

		// Attempt to extract JWT token from cookies
		if ((cookies == null) || (cookies.length == 0)) {
			logger.warn("No cookies");
		} else {
			for (Cookie cookie: cookies) {
				if (cookie.getName().equals("jwt")) {
					jwtToken = cookie.getValue(); // Extract JWT token from cookie
				}
			}
			if (jwtToken == null) {
				logger.warn("No jwt cookie");
			} else {
				try {
					// Extract username from JWT token
					username = jwtTokenUtil.getUsernameFromToken(jwtToken);
				} catch (IllegalArgumentException e) {
					System.out.println("Unable to get JWT Token");
				} catch (ExpiredJwtException e) {
					System.out.println("JWT Token has expired");
				} catch (Exception e) {
					System.out.println("An error occurred");
				}
			}
		}

		// Once we have the username, validate the token
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = this.personDetailsService.loadUserByUsername(username);

			// If token is valid, manually set authentication in Spring Security
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, specify
				// that the current user is authenticated, passing the
				// Spring Security Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		chain.doFilter(request, response); // Continue with the filter chain
	}
}