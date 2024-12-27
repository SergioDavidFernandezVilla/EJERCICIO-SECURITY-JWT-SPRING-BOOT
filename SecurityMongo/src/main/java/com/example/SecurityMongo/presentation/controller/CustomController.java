package com.example.SecurityMongo.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
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

    @GetMapping("/protected")
    @PreAuthorize("hasAuthority('READ')")
    public String Protected(){
        return "Ruta protegida - READ";
    }
}
