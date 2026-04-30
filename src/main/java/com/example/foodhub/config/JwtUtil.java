package com.example.foodhub.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET =
            "foodhubsecretfoodhubsecretfoodhubsecret1234567890"; // 🔥 dài hơn

    private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 ngày

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // 🔥 TẠO TOKEN (FIX ROLE)
    public String generateToken(String email, String role) {

        // đảm bảo luôn đúng format ROLE_
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION)
                )
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔥 PARSE TOKEN
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // 🔥 VALIDATE TOKEN (LOG RÕ LỖI)
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;

        } catch (ExpiredJwtException e) {
            System.out.println("JWT hết hạn");
        } catch (UnsupportedJwtException e) {
            System.out.println("JWT không hỗ trợ");
        } catch (MalformedJwtException e) {
            System.out.println("JWT sai format");
        } catch (SignatureException e) {
            System.out.println("JWT sai chữ ký");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT rỗng hoặc null");
        }

        return false;
    }
}
