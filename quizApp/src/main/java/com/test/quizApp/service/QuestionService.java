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

    public String deleteQuestion(Integer id) {
        if(questionDAO.existsById(id)){
            questionDAO.deleteById(id);
            return "success";
        }
        return "Id not Found";
    }

    public String updateQuestion(Integer id, Question question) {
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
                return "successfully Updated";
            }
        }
        return "Id not found";
    }
}
