package com.test.quizApp.service;


import com.test.quizApp.DAO.QuestionDAO;
import com.test.quizApp.DAO.QuizDAO;
import com.test.quizApp.model.Question;
import com.test.quizApp.model.Quiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    QuizDAO quizDAO;

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions=questionDAO.findRandomQuestionsByCategory(numQ,category);
        Quiz quiz=new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDAO.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }
}
