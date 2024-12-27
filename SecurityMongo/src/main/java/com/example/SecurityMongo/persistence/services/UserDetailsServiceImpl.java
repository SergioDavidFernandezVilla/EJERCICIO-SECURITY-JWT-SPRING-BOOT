package com.example.SecurityMongo.persistence.services;

import com.example.SecurityMongo.persistence.dto.AuthCreateUserDTO;
import com.example.SecurityMongo.persistence.dto.AuthRequestLoginDTO;
import com.example.SecurityMongo.persistence.dto.AuthResponseDTO;
import com.example.SecurityMongo.persistence.entity.RoleEntity;
import com.example.SecurityMongo.persistence.entity.UserEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.SecurityMongo.persistence.repository.RoleRepository;
import com.example.SecurityMongo.persistence.repository.UserRepository;
import com.example.SecurityMongo.utils.JwtUtils;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RoleRepository roleRepository;

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
    
    public AuthResponseDTO loginUser(AuthRequestLoginDTO loginAuth){

        String username = loginAuth.username();
        String password = loginAuth.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String acceesToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, password, "Login success", acceesToken, true);

        return authResponseDTO;
    }

    public Authentication authenticate(String username, String password){
        UserDetails userDetail = this.loadUserByUsername(username);

        if (userDetail == null){
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetail.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, userDetail.getPassword(), userDetail.getAuthorities());
    }

    public AuthResponseDTO createUser(AuthCreateUserDTO authCreateUser) {
        String username = authCreateUser.username();
        String password = authCreateUser.password();
        List<String> rolesRequest = authCreateUser.role().roleListName();

        Set<RoleEntity> rolesEntity = roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest)
        .stream().collect(Collectors.toSet());

        if(rolesEntity.isEmpty()){
            throw new IllegalArgumentException("Los roles ingresados no existen!");
        }

        UserEntity userEntity = UserEntity.builder()
        .username(username).password(passwordEncoder.encode(password))
        .role(rolesEntity).isEnable(true).accountNoLocked(true)
        .accountNoExpired(true).credentialsNoExpired(true).
        build();

        UserEntity userCreated = userRepository.save(userEntity);
        
        ArrayList<SimpleGrantedAuthority> authorityList =  new ArrayList<>();

        userCreated.getRole().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userCreated.getRole()
        .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleEnum().name()))));

        userCreated.getRole().stream()
        .flatMap(role -> role.getPermissions().stream())
        .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getName())));

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userCreated.getUsername()
        ,userCreated.getPassword(), authorityList);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponseDTO authResponseDTO = new AuthResponseDTO(userCreated.getUsername(), userCreated.getPassword(), "User created", accessToken, true);

        return authResponseDTO;
    }
}
