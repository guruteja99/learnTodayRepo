package com.learnToday.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.AuthenicationRequest;
import com.learnToday.util.JwtUtil;

class AuthorisationControllerTest {

	
	
	@Mock
	private UserDetailsService userDetailsService;

	@Mock
	private AuthenticationManager authenicationManager;
	
	@Mock
	private JwtUtil jwtTokenUtil;
	
	@InjectMocks
	private AuthorisationController authorisationController;
	
	@Rule
	MockitoRule rule = MockitoJUnit.rule();

	
	ObjectMapper writer = new ObjectMapper();
	
	private MockMvc mockMvc;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(authorisationController).build();
	}

	@Test
	void testCreateAnthenticationToken() throws Exception {
		Boolean result = true;
		AuthenicationRequest authenicationRequest = new AuthenicationRequest("admin","password");
		User user = new User("admin","password",new ArrayList<>());
		Mockito.when(userDetailsService.loadUserByUsername(anyString())).thenReturn(user);
		Mockito.when(jwtTokenUtil.generateToken(any())).thenReturn("xytoken");
		String content = writer.writeValueAsString(authenicationRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		assertTrue(result);
	}
	
	@Test
	void testCreateAnthenticationToken_Exception() throws Exception {
		Mockito.doThrow(BadCredentialsException.class).when(authenicationManager).authenticate(any());
		AuthenicationRequest authenicationRequest = new AuthenicationRequest("admin","password");
		String content = writer.writeValueAsString(authenicationRequest);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/authenticate").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
	}

}
