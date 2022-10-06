package com.learnToday.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learnToday.dao.TrainerDao;
import com.learnToday.exception.ResourceNotFoundException;
import com.learnToday.models.Trainer;

import lombok.extern.apachecommons.CommonsLog;

@Service
@CommonsLog
public class TrainerService {
	
	@Autowired
	private TrainerDao trainerDao;

	public Trainer addTrainer(Trainer trainer) {
		log.info("In TrainerService - addTrainer with params "+trainer);
		Trainer createdTrainer = trainerDao.save(trainer);
		return createdTrainer;
	}

	public Trainer updatePassword(Integer id, Trainer trainer) {
		log.info("In TrainerService - updatePassword for id "+id+" trainer-"+trainer);
		Optional<Trainer> dbTrainer = trainerDao.findById(id);
		if(dbTrainer.isPresent()) {
			dbTrainer.get().setPassword(trainer.getPassword());
			trainerDao.save(dbTrainer.get());
			log.info("Trainer updates password");
			return dbTrainer.get();
		}else {
			log.info("Exception occured");
			throw new ResourceNotFoundException("Trainer with Id "+id+" not found");
		}
		
	}
	
	
	

}
