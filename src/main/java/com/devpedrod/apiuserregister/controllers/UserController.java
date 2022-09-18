package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.dao.IUserDAO;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.user.UserDto;
import com.devpedrod.apiuserregister.strategy.user.UserRules;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private UserRules strategy;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user){
        userDAO.save(user, strategy::applyBusinessRule);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        BeanUtils.copyProperties(user, userDAO.getById(id));
        userDAO.update(user, strategy::applyBusinessRule);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        userDAO.delete(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<User> disableUser(@PathVariable Long id){
        userDAO.disable(id);
        return ResponseEntity.noContent().build();
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
