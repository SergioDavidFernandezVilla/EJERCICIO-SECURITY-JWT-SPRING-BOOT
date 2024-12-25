package com.example.SecurityMongo.persistence.services;

import com.example.SecurityMongo.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.SecurityMongo.persistence.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity userEntity = userRepository.findUserEntityByUsername(username)
       .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        
        userEntity.getRole().forEach(role -> {
            authorityList.add
            (
                new SimpleGrantedAuthority
                (
                    "ROLE_".concat
                    (
                        role.getRoleEnum().name()
                    )
                )
            );
        });

        userEntity.getRole().stream()
        .flatMap(role -> role.getPermissions().stream())
        .forEach(permission -> authorityList.add(
            new SimpleGrantedAuthority(permission.getName())
        ));

       return new User
       (
        userEntity.getUsername(), 
        userEntity.getPassword(),
        userEntity.isEnable(),
        userEntity.isAccountNoExpired(),
        userEntity.isAccountNoLocked(),
        userEntity.isCredentialsNoExpired(),
        authorityList
        );
    }
    
}
