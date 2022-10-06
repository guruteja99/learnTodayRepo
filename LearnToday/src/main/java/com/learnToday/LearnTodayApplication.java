package com.learnToday;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@CrossOrigin
public class LearnTodayApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnTodayApplication.class, args);
	}

}
