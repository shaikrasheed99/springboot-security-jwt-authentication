package com.springsecurity.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Service
public class JwtService {
    @Value("${jwt.secretKey}")
    public String key;

    public String generateToken(String username, String role) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        long currentTime = System.currentTimeMillis();

        return Jwts
                .builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(currentTime))
                .expiration(new Date(currentTime + convertMinutesToMilliseconds(10)))
                .signWith(getSecretKey())
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(key.getBytes());
    }

    private int convertMinutesToMilliseconds(int minutes) {
        return minutes * 60 * 1000;
    }


    private boolean isTokenExpired(String token) {
        long currentTime = System.currentTimeMillis();
        return extractExpirationTime(token).before(new Date(currentTime));
    }

    private Date extractExpirationTime(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    public String extractUsername(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
