package com.example.SecurityMongo.persistence.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
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
public class RoleEntity {
    
    @Id
    private String id;

    @Field(name = "role_name")
    private RoleEnum roleEnum;

    @Field(name = "permissions")
    private List<PermissionEntity> permissions;
}
