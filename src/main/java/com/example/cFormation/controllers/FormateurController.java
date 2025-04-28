package com.example.cFormation.controllers;

import com.example.cFormation.dto.FormateurDTO;
import com.example.cFormation.dto.FormateurStatsDto;
import com.example.cFormation.models.Employeur;
import com.example.cFormation.models.Formateur;
import com.example.cFormation.services.FormateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/formateurs")
public class FormateurController {

    private final FormateurService formateurService;

    @Autowired
    public FormateurController(FormateurService formateurService) {
        this.formateurService = formateurService;
    }

    // Création avec gestion des relations
    @PostMapping
    public ResponseEntity<Formateur> createFormateur(
            @RequestBody Formateur formateur,
            @RequestParam int employeurId) {  // Changé de structureId à employeurId

        Formateur savedFormateur = formateurService.createFormateur(formateur, employeurId);
        return new ResponseEntity<>(savedFormateur, HttpStatus.CREATED);
    }

    // Récupération par ID
    @GetMapping("/{id}")
    public ResponseEntity<FormateurDTO> getFormateurById(@PathVariable int id) {
        FormateurDTO formateurDTO = formateurService.getFormateurDTOById(id);
        return ResponseEntity.ok(formateurDTO);
    }

    // Liste complète
    @GetMapping
    public List<Formateur> getAllFormateurs() {
        return formateurService.getAllFormateurs();
    }

    // Mise à jour
    @PutMapping("/{id}")
    public ResponseEntity<Formateur> updateFormateur(
            @PathVariable int id,
            @RequestBody Formateur formateurDetails) {

        return ResponseEntity.ok(formateurService.updateFormateur(id, formateurDetails));
    }
    // Suppression

    // Suppression
    @DeleteMapping("/{id}")
    @CrossOrigin
    public ResponseEntity<Void> deleteFormateur(@PathVariable int id) {
        formateurService.deleteFormateur(id);
        return ResponseEntity.noContent().build();
    }

    // Récupération par Employeur
    @GetMapping("/by-employeur/{employeurId}")  // Changé de by-structure à by-employeur
    public List<Formateur> getFormateursByEmployeur(@PathVariable int employeurId) {  // Renommé
        return formateurService.getFormateursByEmployeurId(employeurId);  // Renommé
    }
    @GetMapping("/employeurs")
    public List<Employeur> getAllEmployeurs() {
        return formateurService.getAllEmployeurs();
    }
    @GetMapping("/count")
    public ResponseEntity<Long> getFormateursCount() {
        long count = formateurService.countFormateurs();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/top-3-details")
    public ResponseEntity<List<FormateurStatsDto>> getTop3FormateursDetails() {
        List<FormateurStatsDto> result = formateurService.getTop3FormateursWithDetails();
        return ResponseEntity.ok(result);
    }
}