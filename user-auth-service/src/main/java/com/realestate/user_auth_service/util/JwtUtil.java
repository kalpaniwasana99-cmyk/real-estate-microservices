package com.realestate.user_auth_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ආරක්ෂිත ස්ථාවර රහස් යතුරක් (Secret Key)
    private final String SECRET = "mysecretkeymysecretkeymysecretkeymysecretkeymysecretkey";
    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    // ටෝකන් එකක් වලංගු වන කාලය (පැය 24ක්)
    private static final long EXPIRATION_TIME = 86400000;

    // ටෝකන් එක නිර්මාණය කිරීම
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}