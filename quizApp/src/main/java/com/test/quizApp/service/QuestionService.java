package com.test.quizApp.service;

import com.test.quizApp.DAO.QuestionDAO;
import com.test.quizApp.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public ResponseEntity<List<Question>> getAllQuestion() {
        try {
            return new ResponseEntity<>(questionDAO.findAll(), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<List<Question>> getQuestionByCategory(String category) {
        try {
            return new ResponseEntity<>(questionDAO.findByDifficultyLevel(category), HttpStatus.OK);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        try {
            questionDAO.save(question);
            return new ResponseEntity<>("success", HttpStatus.CREATED);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Failure",HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<String> deleteQuestion(Integer id) {
        if(questionDAO.existsById(id)){
            questionDAO.deleteById(id);
            return new ResponseEntity<>("success",HttpStatus.OK);
        }
        return new ResponseEntity<>("Id not Found",HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateQuestion(Integer id, Question question) {
        if (questionDAO.existsById(id)){
            Question existingQuestion=questionDAO.findById(id).orElse(null);
            if(existingQuestion!=null){
                existingQuestion.setQuestionTitle(question.getQuestionTitle());
                existingQuestion.setOption1(question.getOption1());
                existingQuestion.setOption2(question.getOption2());
                existingQuestion.setOption3(question.getOption3());
                existingQuestion.setAnswer(question.getAnswer());
                existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
                questionDAO.save(existingQuestion);
                return new ResponseEntity<>("successfully Updated",HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Id not Found",HttpStatus.BAD_REQUEST);
    }
}
