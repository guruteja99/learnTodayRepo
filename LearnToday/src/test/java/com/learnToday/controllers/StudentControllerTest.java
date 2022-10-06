package com.learnToday.controllers;

import static org.junit.jupiter.api.Assertions.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnToday.exception.ApiError;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.models.Student;
import com.learnToday.service.CourseService;
import com.learnToday.service.StudentService;

class StudentControllerTest {

	@Mock
	private CourseService courseService;
	
	@Mock
	private StudentService studentService;
	
	@InjectMocks
	private StudentController studentController;
	
	private MockMvc mockMvc;
	
	ObjectMapper writer = new ObjectMapper();
	
	Course course = new Course(1,  "title1", "14501","description1","trainer1",new Date());
	Course course1 = new Course(2,  "title2", "14502","description2","trainer2",new Date());
	Student student = new Student(123,456,course);
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
	}
	

	@Test
	void testGetAllCoursesInStudent() throws Exception {
		List<Course> allcourses = new ArrayList<>();
		allcourses.add(course);
		allcourses.add(course1);
		Mockito.when(courseService.getAllCourses()).thenReturn(allcourses);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/courses").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
		mockMvc.perform(requestBuilder).andExpect(status().isOk());
	}
	

	@Test
	void testPostStudent() throws Exception {
		Mockito.when(studentService.addStudent(any())).thenReturn(student);
		String content = writer.writeValueAsString(student);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isCreated());
	}
	
	@Test
	void testPostStudent_Exception() throws Exception {
		Mockito.when(studentService.addStudent(any())).thenThrow(ResourceNotFoundException.class);
		String content = writer.writeValueAsString(student);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(content);
		mockMvc.perform(requestBuilder).andExpect(status().isNotFound());
	}
	

	@Test
	void testDeleteStudentEnrollment() {
		boolean result =true;
		try {
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteStudent/123").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			mockMvc.perform(requestBuilder).andExpect(status().isOk());
		}catch(Exception e) {
			result = false;
		}
		assertTrue(result);	
		}

	@Test
	void testDeleteStudentEnrollment_Exception() {
		Mockito.doThrow(ResourceNotFoundException.class).when(studentService).deleteStudent(anyInt());
		try {
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/deleteStudent/123").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON);
			mockMvc.perform(requestBuilder);
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
		}
}
