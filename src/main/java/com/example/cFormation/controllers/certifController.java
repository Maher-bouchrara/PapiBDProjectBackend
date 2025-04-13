package com.example.cFormation.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cFormation.services.certifService;
import com.example.cFormation.dto.CertificateBatchRequest;


@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/certificates")
public class certifController {

    @PostMapping("/generate")
    public ResponseEntity<String> generateCerts(@RequestBody CertificateBatchRequest request) {
        try {
            certifService.generateCertificatesForParticipants(request);
            return ResponseEntity.ok("Certificats générés avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erreur lors de la génération des certificats.");
        }
    }
}
