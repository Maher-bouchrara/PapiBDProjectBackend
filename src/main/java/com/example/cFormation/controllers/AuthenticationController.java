package com.example.cFormation.controllers;

import com.example.cFormation.models.Role;
import com.example.cFormation.models.LoginRequest;
import com.example.cFormation.security.JwtUtil;
import com.example.cFormation.models.Utilisateur;
import com.example.cFormation.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Optional<Utilisateur> utilisateur = utilisateurService.getAllUtilisateurs().stream()
                .filter(u -> u.getLogin().equals(request.getLogin()) && u.getMotdePasse().equals(request.getMotDePasse()))
                .findFirst();

        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();
            String token =  jwtUtil.generateToken(user);
            return ResponseEntity.ok(Collections.singletonMap("token", token));

        } else {
            throw new RuntimeException("Identifiants invalides");
        }
    }

}
