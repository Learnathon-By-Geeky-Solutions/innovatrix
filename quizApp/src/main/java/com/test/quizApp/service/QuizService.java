package com.test.quizApp.service;


import com.test.quizApp.DAO.QuestionDAO;
import com.test.quizApp.DAO.QuizDAO;
import com.test.quizApp.model.Question;
import com.test.quizApp.model.QuestionWrapper;
import com.test.quizApp.model.Quiz;
import com.test.quizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestion(Integer id) {
        Optional<Quiz> quiz = quizDAO.findById(id);
        List<Question> questionFromDB=quiz.get().getQuestions();
        List<QuestionWrapper> questionForUser=new ArrayList<>();

        for(Question q : questionFromDB){
            QuestionWrapper qw=new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3());
            questionForUser.add(qw);
        }
        return new ResponseEntity<>(questionForUser,HttpStatus.OK);
    }

    public ResponseEntity<Integer> submitQuiz(int id, List<Response> responses) {
        Optional<Quiz> quiz=quizDAO.findById(id);
        List<Question> questions=quiz.get().getQuestions();
        int rightAnswer=0;
        int i=0;

        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getAnswer())){
                rightAnswer++;
            }
            i++;
        }
        return new ResponseEntity<>(rightAnswer,HttpStatus.OK);
    }
}
