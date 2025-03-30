package com.example.cFormation.controllers;

import com.example.cFormation.dto.FormationDTO;
import com.example.cFormation.models.Formation;
import com.example.cFormation.models.Participant;
import com.example.cFormation.services.FormationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/formations")
public class FormationController {

    private final FormationService formationService;

    @Autowired
    public FormationController(FormationService formationService) {
        this.formationService = formationService;
    }

    // Création avec gestion des relations
    @PostMapping
    public ResponseEntity<Formation> createFormation(
            @RequestBody Formation formation,
            @RequestParam int formateurId,
            @RequestParam int domaineId) {

        Formation savedFormation = formationService.createFormation(formation, formateurId, domaineId);
        return new ResponseEntity<>(savedFormation, HttpStatus.CREATED);
    }

    // Récupération par ID
    @GetMapping("/{id}")
    public ResponseEntity<FormationDTO> getFormationById(@PathVariable int id) {
        FormationDTO formationDTO = formationService.getFormationDTOById(id);
        return ResponseEntity.ok(formationDTO);
    }

    // Liste complète
    @GetMapping
    public List<Formation> getAllFormations() {
        return formationService.getAllFormations();
    }

    // Mise à jour
    @PutMapping("/{id}")
    public ResponseEntity<Formation> updateFormation(
            @PathVariable int id,
            @RequestBody Formation formationDetails) {

        return ResponseEntity.ok(formationService.updateFormation(id, formationDetails));
    }

    // Suppression
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFormation(@PathVariable int id) {
        formationService.deleteFormation(id);
        return ResponseEntity.noContent().build();
    }

    // Récupération par Formateur
    @GetMapping("/by-formateur/{formateurId}")
    public List<Formation> getFormationsByFormateur(@PathVariable int formateurId) {
        return formationService.getFormationsByFormateurId(formateurId);
    }

    // Récupération par Domaine
    @GetMapping("/by-domaine/{domaineId}")
    public List<Formation> getFormationsByDomaine(@PathVariable int domaineId) {
        return formationService.getFormationsByDomaineId(domaineId);
    }
    @PostMapping("/{formationId}/participants/{participantId}")
    public ResponseEntity<Void> addParticipantToFormation(
            @PathVariable int formationId,
            @PathVariable int participantId) {

        formationService.addParticipantToFormation(formationId, participantId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{formationId}/participants/{participantId}")
    public ResponseEntity<Void> removeParticipantFromFormation(
            @PathVariable int formationId,
            @PathVariable int participantId) {

        formationService.removeParticipantFromFormation(formationId, participantId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{formationId}/participants")
    public ResponseEntity<Set<Participant>> getFormationParticipants(
            @PathVariable int formationId) {

        return ResponseEntity.ok(formationService.getFormationParticipants(formationId));
    }
}