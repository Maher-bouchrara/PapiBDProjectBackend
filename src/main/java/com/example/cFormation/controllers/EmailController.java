package com.example.cFormation.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.cFormation.dto.EmailDTO;
import com.example.cFormation.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/envoyer")
    public ResponseEntity<Map<String, String>> envoyerEmail(@RequestBody EmailDTO emailRequest) {
        try {
            // Appel du service pour envoyer les e-mails
            emailService.envoyerEmailAuxParticipants(
                emailRequest.getObjet(),
                emailRequest.getMessage(),
                emailRequest.getListeMails()
            );
            
            // Préparer la réponse en cas de succès
            Map<String, String> response = new HashMap<>();
            response.put("message", "E-mails envoyés avec succès !");
            
            return ResponseEntity.ok(response); // Retourner une réponse 200 avec le message
        } catch (Exception e) {
            e.printStackTrace();
            
            // Préparer la réponse en cas d'erreur
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erreur lors de l'envoi des e-mails.");
            
            return ResponseEntity.internalServerError().body(errorResponse); // Retourner une erreur 500 avec le message
        }
    }
}