package com.example.cFormation.controllers;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.cFormation.services.certifService;
import com.example.cFormation.dto.CertificateBatchRequest;


@CrossOrigin(origins = "*") 
@RestController
@RequestMapping("/api/certificates")
public class certifController {


    @PostMapping("/generate")
    public ResponseEntity<Map<String, String>> generateCerts(@RequestBody CertificateBatchRequest request) {
        try {
            certifService.generateCertificatesForParticipants(request);
            
            Map<String, String> response = new HashMap<>();
            response.put("message", "Certificats générés avec succès !");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Erreur lors de la génération des certificats.");
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}