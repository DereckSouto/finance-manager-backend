package com.dsouto.personal_finance_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dsouto.personal_finance_manager.dto.AuthRequest;
import com.dsouto.personal_finance_manager.dto.AuthResponse;
import com.dsouto.personal_finance_manager.dto.RegisterRequest;
import com.dsouto.personal_finance_manager.model.User;
import com.dsouto.personal_finance_manager.repository.UserRepository;
import com.dsouto.personal_finance_manager.security.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest request) {
		System.out.println("HELLO FROM LOGIN");
		  try {
	            Authentication authentication = authenticationManager.authenticate(
	                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
	            );

	            String jwt = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());

	            return ResponseEntity.ok(new AuthResponse(jwt));
	        } catch (BadCredentialsException e) {
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	        }
		
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		if (userRepository.existsByEmail(request.getEmail())) {
			return ResponseEntity.badRequest().body("Email already in use");
		}
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		
		userRepository.save(user);
		return ResponseEntity.ok("User registered successfully");
		
	}
	
}
