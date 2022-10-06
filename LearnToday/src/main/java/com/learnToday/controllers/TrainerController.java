package com.learnToday.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.learnToday.models.Trainer;
import com.learnToday.service.TrainerService;

import lombok.extern.apachecommons.CommonsLog;

@RestController
@CommonsLog
public class TrainerController {

	@Autowired
	private TrainerService trainerService;
	
	@PostMapping("/signUp")
	public ResponseEntity<Object> trainerSignUp(@RequestBody Trainer trainer){
		log.info("In trainer controller - trainerSignup params "+trainer);
		Trainer trainerCreated = trainerService.addTrainer(trainer);
		return ResponseEntity.status(HttpStatus.CREATED).body(trainerCreated);
	}
	
	@PutMapping("/updatePassword")
	public ResponseEntity<Object> updatePassword(Integer id, Trainer trainer){
		log.info("In trainer controller - trainerSignup params "+trainer);
		Trainer updatedTrainer = trainerService.updatePassword(id, trainer);
		return ResponseEntity.status(HttpStatus.OK).body(updatedTrainer);
	}
}
