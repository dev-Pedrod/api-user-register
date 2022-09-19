package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.user.UserDto;
import com.devpedrod.apiuserregister.facade.Facade;
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
    private final String CLASS_NAME = User.class.getName();

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid User user){
        facade.save(user);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user){
        user.setId(id);
        User oldUser = (User) facade.getById(id, CLASS_NAME);
        user.getAddress().setId(oldUser.getAddress().getId());
        BeanUtils.copyProperties(user, oldUser);
        facade.update(user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        facade.delete(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<User> disableUser(@PathVariable Long id){
        facade.disable(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id){
        return ResponseEntity.ok((User) facade.getById(id, CLASS_NAME));
    }

    @GetMapping
    public ResponseEntity<Page<UserDto>> getAll(Pageable pageable){
        return ResponseEntity.ok(facade.getAll(pageable, CLASS_NAME)
                .map(x -> modelMapper.map(x, UserDto.class)));
    }
}