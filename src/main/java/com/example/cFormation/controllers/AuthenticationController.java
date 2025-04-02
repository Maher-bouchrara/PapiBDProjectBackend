package com.example.cFormation.controllers;

import com.example.cFormation.models.Role;
import com.example.cFormation.security.JwtUtil;
import com.example.cFormation.models.Utilisateur;
import com.example.cFormation.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/authenticate")
public class AuthenticationController {

    @Autowired
    private UtilisateurService utilisateurService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint pour authentifier l'utilisateur et générer un token JWT
    @PostMapping
    public String authenticate(@RequestParam String login, @RequestParam String motDePasse) {
        Optional<Utilisateur> utilisateur = utilisateurService.getAllUtilisateurs().stream()
                .filter(u -> u.getLogin().equals(login) && u.getMotdePasse().equals(motDePasse))
                .findFirst();

        // Si les identifiants sont valides, générer un token JWT
        if (utilisateur.isPresent()) {
            Utilisateur user = utilisateur.get();
            return jwtUtil.generateToken(user);
        } else {
            throw new RuntimeException("Identifiants invalides");  // Si les identifiants sont incorrects
        }
    }
}
