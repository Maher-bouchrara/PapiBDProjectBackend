package com.example.cFormation.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    // Filtre pour chaque requête HTTP
    @Override
    protected void doFilterInternal(HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader); // Affiche l'en-tête Authorization

        // Récupérer le token JWT depuis l'en-tête Authorization
        String token = request.getHeader("Authorization");
        System.out.println("Extracted Token: " + token); // Affiche le token extrait

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);  // Extraire le token sans le préfixe "Bearer "

            // Extraire l'ID de l'utilisateur et le rôle depuis le token
            int userId = jwtUtil.extractUserId(token);
            int roleId = jwtUtil.extractRole(token);
            System.out.println("Extracted userId: " + userId + ", roleId: " + roleId); // Vérifie si l'extraction est correcte

            // Si le token est valide, on crée un objet d'authentification et on l'ajoute au contexte de sécurité
            if (jwtUtil.validateToken(token, userId ,roleId)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userId, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        // Continuer avec le reste du filtre
        filterChain.doFilter(request, response);
    }
}
