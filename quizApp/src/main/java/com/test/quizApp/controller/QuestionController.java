package com.test.quizApp.controller;
import com.test.quizApp.Question;
import com.test.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService; //No need to do new or something.

    @GetMapping("/allQuestion")
    public List<Question> getAllQuestion(){
        List<Question> questions = questionService.getAllQuestion();
        System.out.println(questions);
        return questions ;
    }
}
