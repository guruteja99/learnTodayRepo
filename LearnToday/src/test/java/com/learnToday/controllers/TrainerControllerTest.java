package com.learnToday.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Trainer;
import com.learnToday.service.TrainerService;

class TrainerControllerTest {
	
	@Mock
	private TrainerService trainerService;
	
	@InjectMocks
	private TrainerController trainerController;
	
	private MockMvc mockMvc;
	
	ObjectMapper writer = new ObjectMapper();
	
	Trainer trainer = new Trainer(123,"password");
	Trainer trainer1 = new Trainer(123,"newpassword");

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(trainerController).build();
	}
	

	@Test
	void testTrainerSignUp() throws Exception {
		Mockito.when(trainerService.addTrainer(any())).thenReturn(trainer);
		String content = writer.writeValueAsString(trainer);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/signUp").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}

	@Test
	void testUpdatePassword() throws Exception {
		Mockito.when(trainerService.addTrainer(any())).thenReturn(trainer);
		String content = writer.writeValueAsString(trainer1);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updatePassword").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	@Test
	void testUpdatePassword_Exception() {
		Mockito.when(trainerService.addTrainer(any())).thenThrow(ResourceNotFoundException.class);
		try {
			String content = writer.writeValueAsString(trainer1);
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/updatePassword").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
			mockMvc.perform(requestBuilder).andExpect(status().isOk());
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
	}

}
