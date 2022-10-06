package com.learnToday.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnToday.dao.CourseDao;
import com.learnToday.dao.StudentDao;
import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;
import com.learnToday.models.Student;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class StudentService {

	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private CourseDao courseDao;
	
	public Student addStudent(Student student) throws Exception {
		log.info("In studentService - addStudent with params "+student);
		if(courseDao.findById(student.getCourse().getId()).isPresent()) {
			if(studentDao.findById(student.getEnrollmentId()).isPresent()) {
				throw new ResourceAlreadyExistsException("Enrollement already exists");
			}else {
				Student createdStudent = studentDao.save(student);	
				return createdStudent;
			}
		}else {
			throw new Exception("Course not Available with Id "+student.getCourse().getId());
		}
	}

	public void deleteStudent(Integer id) {
		log.info("In StudentService -deleteStudentEnrollment with params "+id);
		Optional<Student> student = studentDao.findById(id);
		if(student.isPresent()) {
			studentDao.deleteById(id);
			log.info("Student record deleted with id "+id);
		}else {
			log.info("Exception occured");
			throw new ResourceNotFoundException("Course with Id "+id+" not found");
		}
		
	}

}
