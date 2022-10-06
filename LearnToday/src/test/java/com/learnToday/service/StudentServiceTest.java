package com.learnToday.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.learnToday.dao.CourseDao;
import com.learnToday.dao.StudentDao;
import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.models.Student;

class StudentServiceTest {

	@Mock
	private StudentDao dao;
	
	@Mock
	private CourseDao courseDao;
	
	@InjectMocks
	private StudentService service;
	
	private MockMvc mockMvc;
	

	Course course = new Course(123,  "title", "14500","description","trainer",new Date());
	Student student = new Student(new Integer(123),new Integer(456),course);
	
	@BeforeEach
	void setUp(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}

	@Test
	void testAddStudent() {
		Mockito.when(courseDao.findById(anyInt())).thenReturn(Optional.of(course));
		Mockito.when(dao.save(student)).thenReturn(student);
		try{
			Student response = service.addStudent(student);
			assertEquals(123,response.getEnrollmentId());
		}catch(Exception e) {
			System.out.println("Getting exception "+e.getMessage());
			fail();
		}
	}
	
	@Test
	void testAddStudent_exception() {
		Mockito.when(courseDao.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
		try{
			Student response = service.addStudent(student);
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
	}
	
	@Test
	void testAddStudent_exception1() {
		Mockito.when(courseDao.findById(anyInt())).thenThrow(ResourceAlreadyExistsException.class);
		try{
			Student response = service.addStudent(student);
		}catch(Exception e) {
			assertEquals(ResourceAlreadyExistsException.class,e.getClass());
		}
	}
	

	@Test
	void testDeleteStudent() {
		Boolean result = true;
		Mockito.when(dao.findById(anyInt())).thenReturn(Optional.of(student));		
		try{
			service.deleteStudent(1);
		}catch(Exception e) {
			result = false;
		}
		assertTrue(result);
	}
	
	@Test
	void testDeleteStudent_Exception() {
		Mockito.when(dao.findById(anyInt())).thenThrow(ResourceNotFoundException.class);		
		try{
			service.deleteStudent(1);
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
	}

}
