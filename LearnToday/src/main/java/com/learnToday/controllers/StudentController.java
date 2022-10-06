package com.learnToday.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnToday.exception.ApiError;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.models.Student;
import com.learnToday.models.StudentRequest;
import com.learnToday.service.CourseService;
import com.learnToday.service.StudentService;

import lombok.extern.apachecommons.CommonsLog;

@RestController("/Student")
@CommonsLog
public class StudentController {

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping("/courses")
	public ResponseEntity<Object> getAllCoursesInStudent(){
		log.info("In student controller - getAllCourses method");
		List<Course> allCourses = courseService.getAllCourses();
	    return ResponseEntity.status(HttpStatus.OK).body(allCourses);
	}
	
	@PostMapping("/post")
	public ResponseEntity<Object> postStudent(@RequestBody StudentRequest student) throws Exception{
		log.info("In student controller - postStudent with params "+student);
			Student newStudent = new Student(student.getEnrollmentId(),student.getCourseId(),courseService.getCourseById(student.getCourseId()));	
			Student createdStudent = studentService.addStudent(newStudent);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
	}
	
	@DeleteMapping("/deleteStudent/{id}")
	public ResponseEntity<Object> deleteStudentEnrollment(@PathVariable Integer id){
		log.info("In student controller - deleteStudent with params "+id);
		try {
			studentService.deleteStudent(id);
			return ResponseEntity.status(HttpStatus.OK).body("Employee with "+id+" deleted");
		}catch(Exception e) {
			log.info("Exception occured in StudentController deleteStudent method");
			throw new ResourceNotFoundException(e.getMessage());
		}
		
	}
}
