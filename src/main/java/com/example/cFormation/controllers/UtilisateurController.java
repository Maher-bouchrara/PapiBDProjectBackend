package com.example.cFormation.controllers;

import com.example.cFormation.dto.UtilisateurDTO;
import com.example.cFormation.models.Utilisateur;
import com.example.cFormation.services.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {


    private final UtilisateurService utilisateurService;
    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService) {
        this.utilisateurService = utilisateurService;
    }


    // Création avec gestion des relations
        @PostMapping
        public ResponseEntity<Utilisateur> createUtilisateur(
                @RequestBody Utilisateur utilisateur,
                @RequestParam int roleId) {

            Utilisateur savedUtilisateur = utilisateurService.createUtilisateur(utilisateur, roleId);
            return new ResponseEntity<>(savedUtilisateur, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<UtilisateurDTO> getUtilisateurById(@PathVariable int id) {
            UtilisateurDTO utilisateurDTO = utilisateurService.getUtilisateurDTOById(id);
            return ResponseEntity.ok(utilisateurDTO);
        }
        // Liste complète
        @GetMapping
        public List<Utilisateur> getAllUtilisateurs() {
            return utilisateurService.getAllUtilisateurs();
        }

        // Mise à jour
        @PutMapping("/{id}")
        public ResponseEntity<Utilisateur> updateUtilisateur(
                @PathVariable int id,
                @RequestBody Utilisateur utilisateurDetails,
                @RequestParam int roleId) {

            return ResponseEntity.ok(utilisateurService.updateUtilisateur(id, utilisateurDetails , roleId));
        }

        // Suppression
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteUtilisateur(@PathVariable int id) {
            utilisateurService.deleteUtilisateur(id);
            return ResponseEntity.noContent().build();
        }

        // Récupération par Structure
        @GetMapping("/by-role/{roleId}")
        public List<Utilisateur> getUtilisateursByRole(@PathVariable int roleId) {
            return utilisateurService.getUtilisateursByRoleId(roleId);
        }

    @GetMapping("/count")
    public ResponseEntity<Long> getUtilisateursCount() {
        long count = utilisateurService.countUtilisateurs();
        return ResponseEntity.ok(count);
    }
    }

