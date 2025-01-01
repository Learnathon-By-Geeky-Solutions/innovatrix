package com.test.quizApp.controller;
import com.test.quizApp.model.Question;
import com.test.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RequestMapping("/question")
@RestController
public class QuestionController {

    @Autowired
    QuestionService questionService; //No need to do new or something.

    @GetMapping("/allQuestion")
    public ResponseEntity<List<Question>> getAllQuestion(){
        return questionService.getAllQuestion();
    }

    @GetMapping("/category/{level}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable("level") String category){
        return questionService.getQuestionByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id){
        return questionService.deleteQuestion(id);
   }

   @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable Integer id, @RequestBody Question question){
        return questionService.updateQuestion(id,question);
   }

}
