package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.Permission;
import com.devpedrod.apiuserregister.facade.Facade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/permission")
public class PermissionController {
    private final String CLASS_NAME = Permission.class.getName();

    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<Void> createPermission(@RequestBody @Valid Permission permission){
        facade.save(permission);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(permission.getId())
                .toUri()).build();
    }

    @GetMapping
    public ResponseEntity<Page<DomainEntity>> getAll(Pageable pageable){
        return ResponseEntity.ok(facade.getAll(pageable, CLASS_NAME));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Permission> getById(@PathVariable Long id){
        return ResponseEntity.ok((Permission) facade.getById(id, CLASS_NAME));
    }

}
