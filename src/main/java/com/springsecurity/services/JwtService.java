package com.springsecurity.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecretKeyBuilder;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    public String generateToken(String username, String role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + getMillisecondsFromMinutes(10)))
                .signWith(getSecretKey())
                .compact();
    }

    private int getMillisecondsFromMinutes(int minutes) {
        return minutes * 60 * 1000;
    }

    private Key getSecretKey() {
        SecretKeyBuilder secret = Jwts.SIG.HS256.key();
        return Keys.hmacShaKeyFor(secret.build().getEncoded());
    }
}
