package com.example.SecurityMongo.persistence.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "persons")
public class Person {
    
    @Id
    private Integer id;
    private String name;
    private String lastName;
    private String email;
    private String password;
}
