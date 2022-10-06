package com.learnToday.models;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

	@Id
	private Integer Id;
	
	private String title;
	
	private String fees;
	
	private String description;
	
	private String trainer;
	
	private Date startDate;
	
	@OneToMany
	private Set<Student> student;

	public Course(Integer id, String title, String fees, String description, String trainer, Date startDate) {
		super();
		Id = id;
		this.title = title;
		this.fees = fees;
		this.description = description;
		this.trainer = trainer;
		this.startDate = startDate;
	}
	
	
}
