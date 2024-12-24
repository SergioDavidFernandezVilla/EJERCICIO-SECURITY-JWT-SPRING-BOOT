package com.example.SecurityMongo.presentation.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SecurityMongo.config.annotation.isEmpleado;

@RestController
@RequestMapping("/auth")
@PreAuthorize("denyAll()")
public class CustomController {
    
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('{READ}')")
    public String HiAdmin(){
        return "Hi world, Admin";
    }

    @GetMapping("/user")
    @isEmpleado("{USER}")
    public String HiUser(){
        return "Hi world, User";
    }

    @GetMapping("/invited")
    @isEmpleado("{INVITED}")
    public String HiInvited(){
        return "Hi world, Invited";
    }

    @GetMapping("/index")
    @PreAuthorize("permitAll()")
    public String index(){
        return "Index";
    }
}
