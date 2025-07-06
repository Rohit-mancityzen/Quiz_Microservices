package com.rohit.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rohit.model.Quiz;

public interface QuizDao extends JpaRepository<Quiz, Integer>{

}
