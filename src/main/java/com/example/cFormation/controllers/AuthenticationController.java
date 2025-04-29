package com.example.cFormation.controllers;

import com.example.cFormation.models.Role;
import com.example.cFormation.models.LoginRequest;
import com.example.cFormation.security.JwtUtil;
import com.example.cFormation.models.Utilisateur;
import com.example.cFormation.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8094")
@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint pour authentifier l'utilisateur et générer un token JWT
    @PostMapping
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody LoginRequest request) {
        // Input validation
        if (request == null || request.getLogin() == null || request.getMotDePasse() == null) {
            throw new IllegalArgumentException("Login and password must be provided");
        }

        System.out.println("Login request: " + request.getLogin());

        Optional<Utilisateur> utilisateur = utilisateurService.getAllUtilisateurs().stream()
                .filter(u -> {
                    // Safe null checks for user credentials
                    String userLogin = u.getLogin();
                    String userPassword = u.getMotdePasse();
                    return userLogin != null && userPassword != null
                            && userLogin.equals(request.getLogin())
                            && userPassword.equals(request.getMotDePasse());
                })
                .findFirst();

        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();
            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Identifiants invalides");
        }
    }

    @GetMapping("/userId")
    public ResponseEntity<Map<String, Integer>> getUserId(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        int userId = jwtUtil.extractUserId(token);
        return ResponseEntity.ok(Map.of("userId", userId));
    }

    @GetMapping("/roleId")
    public ResponseEntity<Map<String, Integer>> getRoleId(@RequestHeader("Authorization") String authHeader) {
        String token = extractToken(authHeader);
        int roleId = jwtUtil.extractRole(token);
        return ResponseEntity.ok(Map.of("roleId", roleId));
    }

    // Helper method to clean up "Bearer " prefix
    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove "Bearer " prefix
        }
        throw new IllegalArgumentException("Invalid Authorization header format");
    }

}
