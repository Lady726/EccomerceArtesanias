package com.ecommerce.demo.config;

import io.jsonwebtoken.*;

import java.util.Date;

import org.springframework.stereotype.Component;

// Clase JwtUtil utilizada para generar, validar y manejar tokens JWT en el sistema
@Component
// Anotación de Spring que marca esta clase como un componente para su escaneo automático
public class JwtUtil {

  // Clave secreta utilizada para firmar los tokens JWT
  private String secret = "secretKey";

    // Método para generar un token JWT con el nombre de usuario proporcionado
  public String generateToken(String username) {
    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  // Método para validar si un token JWT es válido
  public boolean validateToken(String token) {
    try {
      Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return true;
    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException ex) {
      // log exception
    }
    return false;
  }

  public String extractUsername(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  public Date extractExpiration(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }
}
