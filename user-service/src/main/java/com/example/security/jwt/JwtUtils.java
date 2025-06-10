package com.example.security.jwt;

import com.example.model.User;
import com.example.security.services.UserDetailsImpl;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    //@Value("${jwt.secret}")
    private String jwtSecret = "r1Wq3Y7Ql8ivrpC4A6vEgBg1kllCKCrFkwhbZUNZ6Y8=";

    //@Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs = 1800000;

    public String generateJwtToken(Authentication authentication) {//Authentication authentication User user из логина почту берем для токена

        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        return Jwts.builder()
                //.setSubject((userPrincipal.getUsername()))
                .setSubject(userPrincipal.getEmail())
                .claim("userId", userPrincipal.getId())
                .setIssuedAt(new Date())//дата создания токена
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))//дата конца токена
                .signWith(key(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key())
                .build()
                .parseClaimsJwt(token)
                .getBody();
    }

    public UUID getUserId(String token) {
        String userId = getClaimsFromToken(token).get("userId", String.class);
        return UUID.fromString(userId);
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}
