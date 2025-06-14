package com.backend.internalhackthon.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Configuration;

import java.security.Key;
import java.util.Date;
@Configuration
public class Security {
    public static final String SECRET_KEY_STRING = "MANISHPATIDARCLASS1221235856ATKSJALFHLASFHALKSDFJL";
    public final Key secretKey = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes());


    public String generateToken(String email , String role) {
        String token = Jwts.builder().setSubject(email).claim("role",role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 300 * 5000))
                .signWith(secretKey)
                .compact();
        return "Bearer " + token;

    }

    public boolean validateToken(String token, String email) {
        Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
        return email.equals(extractEmail(token));

    }

    public String extractEmail(String token) {
        Claims claims = extractClaims(token);
        return claims.getSubject();
    }

    public String extractRole(String token) {
        token = token.substring(7);
        Claims claims = extractClaims(token);
        return claims.get("role", String.class); // Ensure the claim key is correct
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
