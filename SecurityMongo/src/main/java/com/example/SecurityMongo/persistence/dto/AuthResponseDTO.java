package com.example.SecurityMongo.persistence.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "username",
    "password",
    "message",
    "token",
    "status"
})
public record AuthResponseDTO(String username,String password,  String message, String token, boolean status) {
    
}
