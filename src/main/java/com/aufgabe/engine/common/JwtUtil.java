package com.aufgabe.engine.common;

import com.aufgabe.engine.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    private static final Long EXPIRE_DURATION = 15L * 60L * 1000L;

    public static String generateJwtToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuer("AUF-ENGINE")
                .setIssuedAt(new Date())
                .addClaims(Map.of(
                        "username", user.getUsername(),
                        "email", user.getEmail(),
                        "privilege", user.getPrivilege()
                ))
                .signWith(SignatureAlgorithm.HS512, user.getUsername())
                .setExpiration(new Date(new Date().getTime() + EXPIRE_DURATION))
                .compact();
    }

}
