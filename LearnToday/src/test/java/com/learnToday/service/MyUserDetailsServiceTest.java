package com.learnToday.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class MyUserDetailsServiceTest {
	
	@InjectMocks
	private MyUserDetailsService service;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}

	@Test
	void testLoadUserByUsername() {
		//doReturn(new User("admin","password",new ArrayList<>()));
		UserDetails response = service.loadUserByUsername("admin");
		assertNotNull(response);
	}

}
