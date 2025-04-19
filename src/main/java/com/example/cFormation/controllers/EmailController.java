package com.example.cFormation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cFormation.dto.EmailDTO;
import com.example.cFormation.services.EmailService;

@RestController
@RequestMapping("/api/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/envoyer")
    public String envoyerEmail(@RequestBody EmailDTO emailRequest) {
        emailService.envoyerEmailAuxParticipants(
            emailRequest.getObjet(),
            emailRequest.getMessage(),
            emailRequest.getListeMails()
        );
        return "E-mails envoyés avec succès !";
    }
}
