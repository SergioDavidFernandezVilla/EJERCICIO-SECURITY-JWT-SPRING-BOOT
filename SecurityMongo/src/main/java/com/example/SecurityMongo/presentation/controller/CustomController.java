package com.example.SecurityMongo.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CustomController {
    
    @GetMapping("/admin")
    public String HiAdmin(){
        return "Hi world, Admin";
    }

    @GetMapping("/user")
    public String HiUser(){
        return "Hi world, User";
    }

    @GetMapping("/invited")
    public String HiInvited(){
        return "Hi world, Invited";
    }
}
