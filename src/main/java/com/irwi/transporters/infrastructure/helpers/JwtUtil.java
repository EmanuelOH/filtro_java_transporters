package com.irwi.transporters.infrastructure.helpers;

import com.irwi.transporters.domain.entities.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    public Key getSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken (UserEntity user){
        return Jwts.builder()
                .addClaims(Map.of(
                        "id", user.getId(),
                        "role", user.getRole()
                        )
                )
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public boolean validateToken(String token, String username){
        return (username.equals(extractUserName(token)) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaims (String token, Function<Claims,T> claimsResolver){
        return claimsResolver.apply(extractAllClaims(token));
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
