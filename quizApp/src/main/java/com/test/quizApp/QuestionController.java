package com.test.quizApp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("question") //Whatever path is coming for question-> this controller will handle it
public class QuestionController {

    @GetMapping("allQuestion")
    public String getAllQuestion(){
        return "Hi, These are your Questions.";
    }
}
