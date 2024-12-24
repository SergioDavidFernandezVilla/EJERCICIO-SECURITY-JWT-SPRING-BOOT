package com.example.SecurityMongo.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecurityMongo.persistence.entity.Person;
import com.example.SecurityMongo.persistence.repository.PersonRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("denyAll()")
public class PersonController {


    private final PersonRepository personRepository;

    @PostMapping("/create/user")
    @PreAuthorize("hasAuthority('{CREATE}')")
    public ResponseEntity<Person> createuser(@RequestBody Person person){
        personRepository.save(person);
        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }
}
