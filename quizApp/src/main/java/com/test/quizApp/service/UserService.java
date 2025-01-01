package com.test.quizApp.service;

import com.test.quizApp.model.User;
import com.test.quizApp.DAO.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public ResponseEntity<User> addUser(User user) {

        User addedUser=userDAO.save(user);
        return new ResponseEntity<>(addedUser, HttpStatus.OK);

    }

    public ResponseEntity<Optional<User>>getUserById(int userId) {
        Optional<User> newUser=userDAO.findById(userId);
        return new ResponseEntity<>(newUser,HttpStatus.OK);
    }
}
