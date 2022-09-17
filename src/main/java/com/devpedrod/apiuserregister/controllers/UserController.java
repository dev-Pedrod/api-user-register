package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.dao.impl.UserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import com.devpedrod.apiuserregister.strategy.user.UserRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private UserRules strategy;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        userDAO.save(user, strategy::applyBusinessRule);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok(userDAO.getById(id));
    }
}
