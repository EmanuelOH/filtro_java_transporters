package com.irwi.transporters.infrastructure.helpers;

import com.irwi.transporters.domain.entities.UserEntity;
import io.jsonwebtoken.*;
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

    // Obtención de la clave de firma para la autenticación
    public Key getSignInKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    // Generación de un token JWT a partir del usuario (usando solo un rol)
    public String generateToken(UserEntity user) {
        return Jwts.builder()
                .addClaims(Map.of(
                        "id", user.getId(),
                        "role", user.getRole() // Usar solo un rol
                ))
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Extraer el nombre de usuario del token
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    // Validar si el token es válido (basado en el username y la expiración)
    public boolean validateToken(String token, String username) {
        return (username.equals(extractUserName(token)) && !isTokenExpired(token));
    }

    // Verificar si el token ha expirado
    public boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    // Extraer una "claim" del token (e.g., roles, expiration, etc.)
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    // Extraer todas las "claims" del token
    public Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSignInKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            // Log or handle token parsing errors (invalid token format, signature issue, etc.)
            throw new IllegalArgumentException("Invalid JWT token", e);
        }
    }

    // Extraer el rol desde el token (solo un rol)
    public String extractRole(String token) {
        return extractClaims(token, claims -> claims.get("role", String.class)); // Ahora devuelve un String en vez de una lista
    }
}
