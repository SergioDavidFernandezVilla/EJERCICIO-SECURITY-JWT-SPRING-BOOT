package com.example.SecurityMongo;

import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.SecurityMongo.persistence.entity.PermissionEntity;
import com.example.SecurityMongo.persistence.entity.RoleEntity;
import com.example.SecurityMongo.persistence.entity.RoleEnum;
import com.example.SecurityMongo.persistence.entity.UserEntity;
import com.example.SecurityMongo.persistence.repository.UserRepository;

@SpringBootApplication
public class SecurityMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityMongoApplication.class, args);
	}

	@Bean
	CommandLineRunner init(UserRepository userRepository) {
		return args -> {
			/* Creación de permisos */
			PermissionEntity createPermission = PermissionEntity.builder()
			.name("CREATE")
			.build();

			PermissionEntity readPermission = PermissionEntity.builder()
			.name("READ")
			.build();

			PermissionEntity updatePermission = PermissionEntity.builder()
			.name("UPDATE")
			.build();

			PermissionEntity deletePermission = PermissionEntity.builder()
			.name("DELETE")
			.build();

			/* CREACION ROLEs */

			RoleEntity AdminRole = RoleEntity.builder()
			.roleEnum(RoleEnum.ADMIN)
			.permissions(Set.of(createPermission, readPermission, updatePermission, deletePermission))
			.build();

			RoleEntity UserRole = RoleEntity.builder()
			.roleEnum(RoleEnum.USER)
			.permissions(Set.of(createPermission, readPermission))
			.build();

			RoleEntity InvitedRole = RoleEntity.builder()
			.roleEnum(RoleEnum.INVITED)
			.permissions(Set.of(readPermission))
			.build();

			/* CREACION USUARIOS */
			UserEntity david = UserEntity.builder()
			.username("david")
			.password("naruto12")
			.isEnable(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialsNoExpired(true)
			.role(Set.of(AdminRole, UserRole))
			.build();

			UserEntity user = UserEntity.builder()
			.username("user")
			.password("1234")
			.isEnable(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialsNoExpired(true)
			.role(Set.of(UserRole))
			.build();

			UserEntity invited = UserEntity.builder()
			.username("invited")
			.password("1234")
			.isEnable(true)
			.accountNoExpired(true)
			.accountNoLocked(true)
			.credentialsNoExpired(true)
			.role(Set.of(InvitedRole))
			.build();

			userRepository.saveAll(List.of(david, user, invited));
		};
	}
}
