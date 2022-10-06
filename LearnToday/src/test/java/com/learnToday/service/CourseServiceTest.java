package com.learnToday.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.learnToday.dao.CourseDao;
import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;

class CourseServiceTest {

	@Mock
	private CourseDao courseDao;
	
	@InjectMocks
	private CourseService service;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	void setUp(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(service).build();
	}
	
	@Test
	void testGetAllCourses() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1,  "title", "14500","description","trainer",new Date()));
		Mockito.when(courseDao.findAll()).thenReturn(courses);
		List<Course> response = service.getAllCourses();
		assertNotNull(response);
	}

	@Test
	void testGetCourseById() {
		Optional<Course> course = Optional.of(new Course(1,  "title", "14500","description","trainer",new Date()));
		Mockito.when(courseDao.findById(anyInt())).thenReturn(course);
		Course response = service.getCourseById(1);
		assertEquals(1,response.getId());
		
	}
	
	@Test
	void testGetCourseById_exception() {
		Mockito.when(courseDao.findById(anyInt())).thenThrow(ResourceNotFoundException.class);
		try {
			service.getCourseById(1);
		}catch(Exception e) {
			assertEquals(ResourceNotFoundException.class,e.getClass());
		}
	}

	@Test
	void testAddCourse() {
		Course course = new Course(1,  "title", "14500","description","trainer",new Date());
		Mockito.when(courseDao.save(course)).thenReturn(course);
		Course response = null;
		try {
			response = service.addCourse(course);
		} catch (ResourceAlreadyExistsException e) {
			e.getMessage();
		}
		assertEquals(1,response.getId());
	}
	
	@Test
	void testAddCourse_Exception() {
		Course course = new Course(1,  "title", "14500","description","trainer",new Date());
		Mockito.when(courseDao.findById(anyInt())).thenThrow(ResourceAlreadyExistsException.class);
		Course response = null;
		try {
			response = service.addCourse(course);
		} catch (Exception e) {
			assertEquals(ResourceAlreadyExistsException.class,e.getClass());
		}
	}
}
