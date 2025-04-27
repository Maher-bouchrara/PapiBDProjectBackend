package com.example.cFormation.controllers;

import com.example.cFormation.models.Employeur;
import com.example.cFormation.services.EmployeurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/employeurs")
public class EmployeurController {

    @Autowired
    private EmployeurService employeurService;

    @GetMapping
    public List<Employeur> getAllEmployeurs() {
        return employeurService.getAllEmployeurs();
    }

    @GetMapping("/{id}")
    public Optional<Employeur> getEmployeurById(@PathVariable("id")  int id) {
        return employeurService.getEmployeurById(id);
    }

   

    @PostMapping
    public Employeur createEmployeur(@RequestBody Employeur employeur) {
        return employeurService.createEmployeur(employeur);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployeur(@PathVariable("id") int id) {
        try {
            employeurService.deleteEmployeur(id); // Suppression dans le service
            return ResponseEntity.ok(new MessageResponse("Employeur supprimé avec succès."));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse("Impossible de supprimer l'employeur car il est associé à des formateurs."));
        }
    }

    // Classe interne pour la réponse JSON
    static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    
    
    @PutMapping("/{id}")
    public Employeur updateEmployeur(@PathVariable("id") int id, @RequestBody Employeur employeur) {
        return employeurService.updateEmployeur(id, employeur);
    }


}
