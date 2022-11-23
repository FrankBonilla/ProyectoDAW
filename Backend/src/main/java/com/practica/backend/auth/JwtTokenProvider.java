package com.practica.backend.auth;


import com.practica.backend.controller.EmpleadoController;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LogManager.getLogger(JwtTokenProvider.class);
    @Value("${app.jwt-secret}")
    private String jwtSecret;

    private Key getSigningKey(){

        byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    @Value("${app.jwt-expiration-milliseconds:3600000}")
    private int jwtExpirationInMs;

    public String generatorToken(Authentication authentication){
        String username = authentication.getName();
        Date today = new Date();
        Date fechaExpiration = new Date(today.getTime() + jwtExpirationInMs);

        final String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String token = Jwts.builder().setSubject(username)
                .claim("roles",authorities)
                .setIssuedAt(new Date())
                .setExpiration(fechaExpiration)
                .signWith(getSigningKey())
                .compact();

        return token;
    }

    public String getUsernameOfToken(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();

        return claims.getSubject();
    }

    public Boolean tokenValidator(String token){

        try{
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            LOGGER.info("Token validado");
            return true;
        }catch(SignatureException ex){
            LOGGER.error("Error al validar el token: "+ex.getMessage());
            throw new SignatureException("Error al validar el token "+ex.getMessage());

        }catch (MalformedJwtException ex){
            throw new MalformedJwtException("Token no válido "+ex.getMessage());

        }catch (ExpiredJwtException ex){
            LOGGER.error("Token caducado: "+ex.getMessage());
            return false;

        }catch (UnsupportedJwtException ex){
            LOGGER.error("Token no compatible: "+ex.getMessage());
            return false;

        }catch (IllegalArgumentException ex){
            LOGGER.error("La cadena claims JWT esta vacía: "+ex.getMessage());
            return false;
        }
    }
}
