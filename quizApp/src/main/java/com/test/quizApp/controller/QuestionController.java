package com.test.quizApp.controller;
import com.test.quizApp.Question;
import com.test.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/category/{level}")
    public List<Question> getQuestionByCategory(@PathVariable("level") String category){
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("/add")
    public String addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

   @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
   }

}
