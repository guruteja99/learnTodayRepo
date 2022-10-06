package com.learnToday.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.service.CourseService;


class AdminControllerTest {
	
	@Mock
	private CourseService service;
	
	@InjectMocks
	private AdminController adminController;
	
	private MockMvc mockMvc;
	
	ObjectMapper writer = new ObjectMapper();
	
	Course course = new Course(1,  "title1", "14501","description1","trainer1",new Date());
	Course course1 = new Course(2,  "title2", "14502","description2","trainer2",new Date());

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
	}

	@Test
	void testGetAllCourses() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		courses.add(course1);
		Mockito.when(service.getAllCourses()).thenReturn(courses);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Admin/courses").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}

	@Test
	void testGetCourseById() throws Exception {
		Mockito.when(service.getCourseById(anyInt())).thenReturn(course);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Admin/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	
	@Test
	void testGetCourseById_Exception() throws Exception{
		Mockito.when(service.getCourseById(anyInt())).thenThrow(ResourceNotFoundException.class);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/Admin/1").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	

	@Test
	void testAddCourse() throws Exception {
		Mockito.when(service.addCourse(any())).thenReturn(course);
		String content = writer.writeValueAsString(course);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Admin/post").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}
	
	@Test
	void testAddCourse_Exception() throws Exception {
		Mockito.when(service.addCourse(any())).thenThrow(ResourceAlreadyExistsException.class);
		String content = writer.writeValueAsString(course);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/Admin/post").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isAlreadyReported());
	}

}
