package com.realestate.user_auth_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // ආරක්ෂිත රහස්‍ය යතුරක් (Secret Key) ස්වයංක්‍රීයව සාදා ගැනීම
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // ටෝකන් එකක් වලංගු වන කාලය (මිලි තත්පර වලින්) - මෙහි පැය 24ක් ලබා දී ඇත
    private static final long EXPIRATION_TIME = 86400000;

    // ටෝකන් එක නිර්මාණය කරන Method එක
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }
}