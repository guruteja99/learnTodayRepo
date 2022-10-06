package com.learnToday.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learnToday.models.AuthenicationRequest;
import com.learnToday.models.AuthenicationResponse;
import com.learnToday.service.MyUserDetailsService;
import com.learnToday.util.JwtUtil;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@CrossOrigin
public class AuthorisationController {

	@Autowired
	private AuthenticationManager authenicationManager;

	@Autowired
	private MyUserDetailsService userDetailsService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;

	@Autowired
	private JwtUtil jwtTokenUtil;

	@PostMapping("/authenticate")
	public ResponseEntity<?> createAnthenticationToken(@RequestBody AuthenicationRequest authenicationRequest)
			throws Exception {
		log.info(authenicationRequest);
		try {

			authenicationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenicationRequest.getUsername(), authenicationRequest.getPassword()));

		} catch (BadCredentialsException e) {
			return ResponseEntity.badRequest().build();
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenicationRequest.getUsername());

		session.setAttribute("userDetails", userDetails);
		
		final String jwt = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new AuthenicationResponse(jwt));
	}
	
}
