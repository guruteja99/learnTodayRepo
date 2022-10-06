package com.learnToday.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.learnToday.models.Trainer;

public interface TrainerDao extends JpaRepository<Trainer, Integer> {

}
