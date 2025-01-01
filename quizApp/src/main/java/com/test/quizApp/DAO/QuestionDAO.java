package com.test.quizApp.DAO;

import com.test.quizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDAO extends JpaRepository<Question,Integer> {
    List<Question> findByDifficultyLevel(String difficultyLevel);

    @Query(value="SELECT * FROM question q WHERE q.difficulty_level=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(int numQ, String category);
}
