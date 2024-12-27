package com.example.SecurityMongo.persistence.dto;


public record AuthCreateUserDTO(String username, String password, AuthRequestRoleDTO role) {
    
}
