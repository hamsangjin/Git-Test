package com.example.oauth.provider;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtProvider {
    @Value("${serect-key}")
    private String serectKey;

    public String createJwt(String userId) {
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));  // 1시간 후 만료
        Key key = Keys.hmacShaKeyFor(serectKey.getBytes(StandardCharsets.UTF_8));   // secret key 생성

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact();

        return jwt;
    }

    // jwt 검증
    public String validateJwt(String jwt) {
        String subject = null;
        Key key = Keys.hmacShaKeyFor(serectKey.getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }

        return subject;
    }
}
