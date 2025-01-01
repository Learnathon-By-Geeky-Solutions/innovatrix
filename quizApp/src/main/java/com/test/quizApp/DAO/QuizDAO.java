package com.test.quizApp.DAO;

import com.test.quizApp.model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizDAO extends JpaRepository<Quiz, Integer> {
}
