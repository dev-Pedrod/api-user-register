package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.Formation;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.facade.Facade;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.stream.Collectors;

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

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFormation(@RequestBody @Valid Formation formation, @PathVariable Long id){
        formation.setId(id);
        Formation oldFormation = (Formation) facade.getById(id, CLASS_NAME);
        formation.setUser(oldFormation.getUser());
        BeanUtils.copyProperties(formation, oldFormation);
        facade.update(formation);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable Long id){
        facade.delete(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<Void> disableFormation(@PathVariable Long id){
        facade.disable(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<DomainEntity>> getAll(Pageable pageable){
        return ResponseEntity.ok(facade.getAll(pageable, CLASS_NAME));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Formation> getById(@PathVariable Long id){
        return ResponseEntity.ok((Formation) facade.getById(id, CLASS_NAME));
    }
}
