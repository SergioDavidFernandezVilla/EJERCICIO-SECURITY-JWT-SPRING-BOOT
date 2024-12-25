package com.example.SecurityMongo.persistence.entity;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class UserEntity {
    
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    private String password;

    @Field(name = "is_enable")
    private boolean isEnable;

    @Field(name = "account_no_expired")
    private boolean accountNoExpired;

    @Field(name = "account_no_locked")
    private boolean accountNoLocked;

    @Field(name = "credentials_no_expired")
    private boolean credentialsNoExpired;
    
    @Field(name = "roles")
    private Set<RoleEntity> role;
}
