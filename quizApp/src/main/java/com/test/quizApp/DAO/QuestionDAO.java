package com.test.quizApp.DAO;

import com.test.quizApp.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question,Integer> {
    List<Question> findByDifficultyLevel(String difficultyLevel);


}
