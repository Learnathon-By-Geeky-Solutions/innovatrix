package com.test.quizApp.service;

import com.test.quizApp.DAO.QuestionDAO;
import com.test.quizApp.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getAllQuestion() {
        List<Question> questions = questionDAO.findAll();
        //System.out.println(questions);
        return questions;

    }

    public List<Question> getQuestionByCategory(String category) {
        return questionDAO.findByDifficultyLevel(category);
    }

    public String addQuestion(Question question) {
        questionDAO.save(question);
        return "success";

    }
}
