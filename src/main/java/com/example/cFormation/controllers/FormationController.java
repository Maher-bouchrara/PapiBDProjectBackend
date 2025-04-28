package com.example.cFormation.controllers;

import com.example.cFormation.dto.DomaineBudgetMensuelDto;
import com.example.cFormation.dto.DomainePourcentageDTO;
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
    public ResponseEntity<Formation> createFormation1(
            @RequestBody Formation formation,
            @RequestParam(name = "formateurId") int formateurId,
            @RequestParam(name = "domaineId") int domaineId) {

        Formation savedFormation = formationService.createFormation(formation, formateurId, domaineId);
        return new ResponseEntity<>(savedFormation, HttpStatus.CREATED);
    }

    // Récupération par ID
    
    
    @GetMapping("/{id}")
    public ResponseEntity<FormationDTO> getFormationById(@RequestParam("id") int id) {
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
    public List<Formation> getFormationsByDomaine(@RequestParam("id") int domaineId) {
        return formationService.getFormationsByDomaineId(domaineId);
    }
    @PostMapping("/{formationId}/participants/{participantId}")
    public ResponseEntity<Void> addParticipantToFormation(
            @PathVariable("formationId") int formationId,
            @PathVariable("participantId") int participantId) {

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
    		@PathVariable("formationId") int formationId) {

        return ResponseEntity.ok(formationService.getFormationParticipants(formationId));
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getFormationsCount() {
        long count = formationService.countFormations();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/stats/domaines")
    public ResponseEntity<List<DomainePourcentageDTO>> getStatsFormationsParDomaine() {
        List<DomainePourcentageDTO> stats = formationService.getFormationsPourcentageByDomaine();
        return ResponseEntity.ok(stats);
    }
    @GetMapping("/stats/budgets-mensuels-top3")
    public ResponseEntity<List<DomaineBudgetMensuelDto>> getBudgetsMoyensParMois() {
        List<DomaineBudgetMensuelDto> stats = formationService.getBudgetsMoyensParMoisTop3Domaines();
        return ResponseEntity.ok(stats);
    }
}