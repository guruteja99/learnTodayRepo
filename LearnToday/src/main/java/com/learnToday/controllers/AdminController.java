package com.learnToday.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.service.CourseService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
@CrossOrigin
public class AdminController {

	@Autowired
	private CourseService courseService;
	
	@GetMapping("/Admin/courses")
	@ApiOperation(value="" , authorizations = { @Authorization(value="jwtToken")})
	public ResponseEntity<Object> getAllCourses(){
		log.info("In Admin Controller -getAllCourses");
		List<Course> allCourses = courseService.getAllCourses();
		return ResponseEntity.status(HttpStatus.OK).body(allCourses);
	}
	
	@GetMapping("/Admin/{id}")
	public ResponseEntity<Object> getCourseById(@PathVariable Integer id){
		log.info("In Admin Controller - getCourseById with paremeter "+id);
		try {
			Course course = courseService.getCourseById(id);
			return ResponseEntity.status(HttpStatus.OK).body(course);
		}catch(Exception e) {
			log.error("Exception occured In Admin Controller");
			throw new ResourceNotFoundException("Course with Id "+id+" not found");
		}		
	}
	
	@PostMapping("/Admin/post")
	public ResponseEntity<Object> addCourse(@RequestBody Course course) throws ResourceAlreadyExistsException{
		log.info("In AdminController - addCourse method with param "+course);
		Course courseCreated = courseService.addCourse(course);
		return ResponseEntity.status(HttpStatus.CREATED).body(courseCreated);
	}

}
