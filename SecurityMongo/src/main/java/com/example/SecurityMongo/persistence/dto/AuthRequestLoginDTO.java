package com.example.SecurityMongo.persistence.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.NonNull;

@JsonPropertyOrder({
    "username",
    "password"
})
public record AuthRequestLoginDTO(@NonNull String username, String password) {
    
}
