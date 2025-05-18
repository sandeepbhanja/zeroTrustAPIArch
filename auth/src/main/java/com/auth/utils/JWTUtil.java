package com.auth.utils;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;


@Component
public class JWTUtil {

    @Value("${jwt.expiration}")
    private long expirationTimeInMs;
    @Value("${jwt.secretKey}")
    String jwtSecret;
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Long _id,String role) {
        Map<String,String> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("_id", _id.toString());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationTimeInMs))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
