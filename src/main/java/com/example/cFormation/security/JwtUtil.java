package com.example.cFormation.security;

import com.example.cFormation.models.Utilisateur;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);  // Generates a key with 256 bits


    //private String secretKey = "VYyfMGmEMtZg5izZ/+e0GCnGLsJAh5P+yAjBWtkb/7k=";  // Utilise une clé secrète sécurisée

    public String generateToken(Utilisateur user) {
        // Récupère roleId au lieu de role.nom
        int roleId = user.getIdRole();

        return Jwts.builder()
                .claim("userId", user.getId())
                .claim("roleId", roleId)
                .setSubject("JWT Token")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // Expiration de 1 heure
                .signWith(SECRET_KEY)
                //.signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public int extractUserId(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("userId", Integer.class);
    }


    public int extractRole(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .get("roleId", Integer.class);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    public boolean validateToken(String token,int userId,int roleId) {
        System.out.println("Token reçu : " + token);
        System.out.println("User ID extrait : " + extractUserId(token));
        System.out.println("Role ID extrait : " + extractRole(token));
        return ( userId==extractUserId(token) && roleId==extractRole(token) && !isTokenExpired(token));
    }
}
