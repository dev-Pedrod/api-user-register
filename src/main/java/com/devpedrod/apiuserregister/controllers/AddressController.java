package com.devpedrod.apiuserregister.controllers;

import com.devpedrod.apiuserregister.domain.Address;
import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.User;
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
@RequestMapping("/api/v1/address")
public class AddressController {
    private final String CLASS_NAME = Address.class.getName();

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Facade facade;

    @PostMapping
    public ResponseEntity<Void> createAddress(@RequestBody @Valid Address address, @RequestParam Long userId){
        address.setUser((User) facade.getById(userId, User.class.getName()));
        address.getUser().setAddress(address);
        facade.save(address);
        return ResponseEntity.created(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(address.getId())
                .toUri()).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Long id, @RequestBody Address address){
        address.setId(id);
        Address oldAdrress = (Address) facade.getById(id, CLASS_NAME);
        address.setUser(oldAdrress.getUser());
        BeanUtils.copyProperties(address, oldAdrress);
        facade.update(address);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Address> deleteAddress(@PathVariable Long id){
        facade.delete(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/disable/{id}")
    public ResponseEntity<Address> disableAddress(@PathVariable Long id){
        Address adrress = (Address) facade.getById(id, CLASS_NAME);
        adrress.getUser().setAddress(null);
        facade.disable(id, CLASS_NAME);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getById(@PathVariable Long id){
        return ResponseEntity.ok((Address) facade.getById(id, CLASS_NAME));
    }

    @GetMapping
    public ResponseEntity<Page<DomainEntity>> getAll(Pageable pageable){
        return ResponseEntity.ok(facade.getAll(pageable, CLASS_NAME));
    }
}
