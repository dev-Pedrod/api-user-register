package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.dao.impl.GenericDAO;
import com.devpedrod.apiuserregister.dao.impl.UserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.UserDto;
import com.devpedrod.apiuserregister.strategy.user.UserRules;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Autowired
    private ModelMapper modelMapper;

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

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(userDAO.getAll(pageable)
                .map(x -> modelMapper.map(x, UserDto.class)));
    }
}
