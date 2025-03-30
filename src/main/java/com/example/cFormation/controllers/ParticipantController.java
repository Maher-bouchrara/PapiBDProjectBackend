package com.example.cFormation.controllers;

import com.example.cFormation.dto.ParticipantDTO;
import com.example.cFormation.models.Participant;
import com.example.cFormation.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantController {

    private final ParticipantService participantService;

    @Autowired
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    // Création avec gestion des relations
    @PostMapping
    public ResponseEntity<Participant> createParticipant(
            @RequestBody Participant participant,
            @RequestParam int profileId,
            @RequestParam int structureId) {

        Participant savedParticipant = participantService.createParticipant(participant, profileId, structureId);
        return new ResponseEntity<>(savedParticipant, HttpStatus.CREATED);
    }

    // Récupération par ID
   /* @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable int id) {
        return participantService.getParticipantById(id)
                .map(participant -> modelMapper.map(participant, ParticipantDTO.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/
    @GetMapping("/{id}")
    public ResponseEntity<ParticipantDTO> getParticipantById(@PathVariable int id) {
        ParticipantDTO participantDTO = participantService.getParticipantDTOById(id);
        return ResponseEntity.ok(participantDTO);
    }
    // Liste complète
    @GetMapping
    public List<Participant> getAllParticipants() {
        return participantService.getAllParticipants();
    }

    // Mise à jour
    @PutMapping("/{id}")
    public ResponseEntity<Participant> updateParticipant(
            @PathVariable int id,
            @RequestBody Participant participantDetails) {

        return ResponseEntity.ok(participantService.updateParticipant(id, participantDetails));
    }

    // Suppression
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParticipant(@PathVariable int id) {
        participantService.deleteParticipant(id);
        return ResponseEntity.noContent().build();
    }

    // Récupération par Structure
    @GetMapping("/by-structure/{structureId}")
    public List<Participant> getParticipantsByStructure(@PathVariable int structureId) {
        return participantService.getParticipantsByStructureId(structureId);
    }

    // Récupération par Profile
    @GetMapping("/by-profile/{profileId}")
    public List<Participant> getParticipantsByProfile(@PathVariable int profileId) {
        return participantService.getParticipantsByProfileId(profileId);
    }
}