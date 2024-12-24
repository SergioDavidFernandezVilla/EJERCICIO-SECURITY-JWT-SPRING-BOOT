package com.example.SecurityMongo.persistence.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.SecurityMongo.persistence.entity.Person;

@Repository
public interface PersonRepository extends MongoRepository<Person, Integer> {
    
}
