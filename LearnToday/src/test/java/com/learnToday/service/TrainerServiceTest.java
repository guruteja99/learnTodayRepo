package com.learnToday.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.learnToday.dao.TrainerDao;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Trainer;

class TrainerServiceTest {

	@Mock
	private TrainerDao trainerDao;
	
	@InjectMocks
	private TrainerService service;
	
	private MockMvc mockMvc;
	
	Trainer trainer = new Trainer(123,"password");
	Trainer trainer1 = new Trainer(123,"newpassword");
	
	@BeforeEach
	void setUp(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}

	@Test
	void testAddTrainer() {
		Mockito.when(trainerDao.save(any())).thenReturn(trainer);
		Trainer response = service.addTrainer(trainer);
		assertEquals(trainer.getTrainerId(),response.getTrainerId());
	}

	@Test
	void testUpdatePassword() {
		Mockito.when(trainerDao.findById(anyInt())).thenReturn(Optional.of(trainer));
		Mockito.when(trainerDao.save(any())).thenReturn(trainer1);
		Trainer response = service.updatePassword(123, trainer1);
		assertEquals(trainer1.getPassword(),response.getPassword());
	}
	
	@Test
	void testUpdatePassword_Exception() {
		Mockito.when(trainerDao.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
		try {
			Trainer response = service.updatePassword(123, trainer1);
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
	}

}
