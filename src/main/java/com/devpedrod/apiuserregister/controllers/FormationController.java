package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.facade.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/formations")
public class FormationController {
    private final String CLASS_NAME = Formation.class.getName();

    @Autowired
    private Facade facade;

    @PostMapping("/{userId}")
    public ResponseEntity<Void> createFormation(@RequestBody @Valid Formation formation, @PathVariable Long userId){
        formation.setUser((User) facade.getById(userId, User.class.getName()));
        formation.getUser().getFormations().add(formation);
        facade.save(formation);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/v1/formations/{id}")
                .buildAndExpand(formation.getId())
                .toUri()).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getById(@PathVariable Long id){
        return ResponseEntity.ok((Formation) facade.getById(id, CLASS_NAME));
    }
}
