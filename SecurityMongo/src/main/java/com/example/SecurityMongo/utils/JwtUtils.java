package com.example.SecurityMongo.utils;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JwtUtils {
    
    @Value("${security.jwt.key.private}")
    private String privateKey;

    @Value("${security.jwt.user.generated}")
    private String userGenerator;

    /* GENERATOR TOKEN */
    public String createToken(Authentication authentication){
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        String username = authentication.getPrincipal().toString();

        String authorities = authentication.getAuthorities()
        .stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.joining(","));

        String jwtToken = JWT.create()
        
        /* Head */
        .withIssuer(this.userGenerator)

        /* Payload */
        .withSubject(username)
        .withClaim("authorities", authorities)
        .withIssuedAt(new Date())
        .withExpiresAt(new Date(System.currentTimeMillis() + 144000000))
        .withJWTId(UUID.randomUUID().toString())
        .withNotBefore(new Date(System.currentTimeMillis()))

        /* Sign */
        .sign(algorithm);

        return jwtToken;
    }

    /* Validate Token */
    public DecodedJWT ValitorJwtToken(String token){
        try {

            /* Algoritmo de cifrado */
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            /* Verificador que valida el token */
            JWTVerifier verifierToken = JWT.require(algorithm)
            .withIssuer(this.userGenerator)
            .build();

            /* Decodifica el token */
            DecodedJWT decodedJWT = verifierToken.verify(token);
            return decodedJWT;
        } catch (JWTDecodeException e) {
            throw new JWTDecodeException("Token invalido, No Authorizado!!");
        }
    }

    /* Obtiene el nombre del usuario mediante el token ingresado.  */
    public String getUsernameByToken(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    /* Obtiene un valor adentro del payload jwt */
    public Claim getClaim(DecodedJWT decodedJWT, String ClainName){
        return decodedJWT.getClaim(ClainName);
    }

    /* Obtiene los valores adentro del payload jwt */
    public Map<String, Claim> getClaims(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
