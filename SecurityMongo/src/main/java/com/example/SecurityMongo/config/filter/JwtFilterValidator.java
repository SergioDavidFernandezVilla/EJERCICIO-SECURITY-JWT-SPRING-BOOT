package com.example.SecurityMongo.config.filter;

import java.io.IOException;
import java.util.Collection;

import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.SecurityMongo.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilterValidator extends OncePerRequestFilter{

    private JwtUtils jwtUtils;

    public JwtFilterValidator(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(
       @NonNull HttpServletRequest request, 
       @NonNull HttpServletResponse response, 
       @NonNull FilterChain filterChain) throws ServletException, IOException {
        
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (token != null){
            token = token.substring(7);

            DecodedJWT decodedJWT = jwtUtils.ValitorJwtToken(token);
            String username = jwtUtils.getUsernameByToken(decodedJWT);
            String Stringauthorities = jwtUtils.getClaim(decodedJWT, "authorities").asString();

            Collection<? extends GrantedAuthority> authorities = 
            AuthorityUtils.commaSeparatedStringToAuthorityList(Stringauthorities);

            SecurityContext context = SecurityContextHolder.getContext();
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            
            context.setAuthentication(authenticationToken);
            SecurityContextHolder.setContext(context);
        }

        doFilter(request, response, filterChain);
    }
    
}
