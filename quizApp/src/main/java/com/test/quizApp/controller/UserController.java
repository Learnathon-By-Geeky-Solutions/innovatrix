package com.test.quizApp.controller;


import com.test.quizApp.model.User;
import com.test.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
   private UserService userService;

   @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
       return userService.addUser(user);
   }

   @GetMapping("/get/{userId}")
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int userId){
       return  userService.getUserById(userId);
   }
}
