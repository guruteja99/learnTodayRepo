package com.learnToday.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.learnToday.models.Course;

public interface CourseDao extends JpaRepository<Course,Integer> {

	@Query(value= "select c from Course c")
	List<Course> findAllCourses(Sort sort);
}
