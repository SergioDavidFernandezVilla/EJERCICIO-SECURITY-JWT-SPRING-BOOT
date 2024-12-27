package com.example.SecurityMongo.presentation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.SecurityMongo.persistence.dto.AuthRequestLoginDTO;
import com.example.SecurityMongo.persistence.dto.AuthResponseDTO;
import com.example.SecurityMongo.persistence.dto.AuthCreateUserDTO;
import com.example.SecurityMongo.persistence.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @GetMapping("/index")
    public String IndexHome(){
        return "Index - AUTH";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody AuthCreateUserDTO authCreateUser){
        return new ResponseEntity<>(this.userDetailsServiceImpl.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestLoginDTO loginAuth){
        return new ResponseEntity<>(this.userDetailsServiceImpl.loginUser(loginAuth), HttpStatus.OK);
    }   
}
