package com.example.SecurityMongo.persistence.entity;

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
public class PermissionEntity {
    
    @Id
    private String id;

    @Field(name = "permission_name")
    @Indexed(unique = true)
    private String permissionName;

    public void setPermissionEnum(PermissionEnum permissionEnum) {
        this.permissionName = permissionEnum.name();
    }

    public PermissionEnum getPermissionEnum() {
        return PermissionEnum.valueOf(this.permissionName);
    }
}
