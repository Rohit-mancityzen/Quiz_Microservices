package com.rohit.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rohit.dao.QuizDao;
import com.rohit.feign.QuizInterface;
import com.rohit.model.QuestionWrapper;
import com.rohit.model.QuizDto;
import com.rohit.model.Response;
import com.rohit.service.QuizService;

@RequestMapping("quiz")
@RestController
public class QuizController {

    private final QuizDao quizDao;
	@Autowired
	QuizService quizService;
	

    QuizController(QuizDao quizDao) {
        this.quizDao = quizDao;
    }
	
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto){
		return quizService.createQuiz(quizDto.getCategory(),quizDto.getNumQuestions(),quizDto.getTitle());
	}
	@GetMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
		return quizService.getQuizQuestions(id);
	}
	
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
		
		return quizService.calculateResult(id,responses);
		
	}

}
