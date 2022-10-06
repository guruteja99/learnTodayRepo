package com.learnToday.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentRequest {

	private Integer enrollmentId;
	
	private Integer studentid;
	
	private Integer courseId;
	
}
