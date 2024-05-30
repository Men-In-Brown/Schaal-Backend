package com.nighthawk.spring_portfolio.mvc.linkrAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.nighthawk.spring_portfolio.mvc.person.Person;
import com.nighthawk.spring_portfolio.mvc.person.PersonDetailsService;

@RestController
@CrossOrigin(origins = "*")
public class LinkrJWTApiController {

    @Autowired
    private AuthenticationManager authenticationManager; // Spring authentication manager for validating user credentials

    @Autowired
    private LinkrJwtTokenUtil jwtTokenUtil; // Utility class for generating JWT tokens

    @Autowired
    private PersonDetailsService employeeService; // Service for managing employee-related operations

    // Endpoint for authenticating users and generating JWT tokens
    @PostMapping("/linkrAuthenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody Person authenticationRequest) throws Exception {
        // Authenticate user credentials
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        // Load user details based on the provided username (email)
        final UserDetails userDetails = employeeService.loadUserByUsername(authenticationRequest.getEmail());
        // Cast the UserDetails to Person if necessary
        if (!(userDetails instanceof Person)) {
            throw new Exception("Invalid user details");
        }
        final Person personDetails = (Person) userDetails;
        // Generate JWT token for the authenticated user
        final String token = jwtTokenUtil.generateToken(personDetails);
        // Create HTTP cookie containing the JWT token
        final ResponseCookie tokenCookie = ResponseCookie.from("jwt", token)
            .httpOnly(true)
            .secure(true)
            .path("/")
            .maxAge(3600)
            .sameSite("None; Secure")
            // .domain("example.com") // Set to backend domain if necessary
            .build();
        // Return the JWT token as a cookie in the response header
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, tokenCookie.toString()).build();
    }

    // Method to authenticate user credentials
    private void authenticate(String username, String password) throws Exception {
        try {
            // Attempt to authenticate using provided username and password
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) { // If user account is disabled
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) { // If provided credentials are invalid
            throw new Exception("INVALID_CREDENTIALS", e);
        } catch (Exception e) { // For other authentication errors
            throw new Exception(e);
        }
    }
}