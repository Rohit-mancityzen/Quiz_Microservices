package com.rohit.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.rohit.dao.QuestionDao;
import com.rohit.model.QuestionWrapper;
import com.rohit.model.Questions;
import com.rohit.model.Response;

@Service
public class QuestionService {
	@Autowired
	QuestionDao questiondao;

	public ResponseEntity<List<Questions>> getallQuestion() {
		try {
			return new ResponseEntity<>(questiondao.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(questiondao.findAll(), HttpStatus.BAD_REQUEST);
	}

	public ResponseEntity<List<Questions>> getQuestionsByCategory(String Category) {
		
		try {
			return new ResponseEntity<>(questiondao.findByCategory(Category), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(questiondao.findByCategory(Category), HttpStatus.BAD_REQUEST);

	}

	public ResponseEntity<String> addQuestion(Questions question) {
		
		try {
			questiondao.save(question);
			return new ResponseEntity<>("Success", HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>("Failed", HttpStatus.BAD_REQUEST);

		
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String categoryName, Integer numberOfQuestions) {
		 List<Integer> questions = questiondao.findRandomQuestionsByCategory(categoryName,numberOfQuestions);
		 return new ResponseEntity<>(questions,HttpStatus.OK);
		 
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) {
		List<QuestionWrapper> wrapper = new ArrayList<>();
		List<Questions> questions = new ArrayList<>();

		for (Integer id : questionIds) {
			questions.add(questiondao.findById(id).get());
		}
		for (Questions question : questions) {
			QuestionWrapper wrap = new QuestionWrapper();
			wrap.setId(question.getId());
			wrap.setQuestionTitle(question.getQuestionTitle());
			wrap.setOption1(question.getOption1());
			wrap.setOption2(question.getOption2());
			wrap.setOption3(question.getOption3());
			wrap.setOption4(question.getOption4());
			wrapper.add(wrap);
		}
		return new ResponseEntity<>(wrapper,HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		int right =0;
		for(Response response : responses) {
			Questions question = questiondao.findById(response.getId()).get();
			if(response.getResponse().equals(question.getRightAnswer())) {
				right++;
			}
		}
		return new ResponseEntity<>(right,HttpStatus.OK);
	}

	
	

}
