package com.learnToday.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.learnToday.dao.CourseDao;
import com.learnToday.exception.ResourceAlreadyExistsException;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Course;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class CourseService {

	@Autowired
	private CourseDao courseDao;
	
	public List<Course> getAllCourses(){
		log.info("In CourseService - get AllCourses method");
		List<Course> courses = courseDao.findAll(Sort.by("startDate"));
		return courses;
	}

	public Course getCourseById(Integer id) {
		log.info("In course service - getCourseById with param "+id);
		Optional<Course> course = courseDao.findById(id);
		if(course.isPresent()) {
			return course.get();
		}else {
			log.info("Exception occured");
			throw new ResourceNotFoundException("Course with Id "+id+" not found");
		}
	}

	public Course addCourse(Course course) throws ResourceAlreadyExistsException {
		log.info("In course service - addCourse with param "+course.toString());
		if(courseDao.findById(course.getId()).isPresent()){	
			throw new ResourceAlreadyExistsException("course with Id "+course.getId()+" already exists");
		}else {
			Course createdCourse = courseDao.save(course);
			return createdCourse;
		}
		
	}
}
